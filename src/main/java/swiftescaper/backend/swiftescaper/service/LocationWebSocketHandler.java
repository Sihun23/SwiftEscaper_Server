package swiftescaper.backend.swiftescaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import swiftescaper.backend.swiftescaper.apiPayload.code.LocationDTO;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LocationWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String token = "";
    private String tunnel = "";

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // WebSocket 연결 성공 메시지 출력
        System.out.println("WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // 클라이언트로부터 받은 메시지(JSON) 처리
            String payload = message.getPayload();
            System.out.println("Received: " + payload);

            // JSON 데이터를 LocationDTO로 변환
            LocationDTO locationDTO = objectMapper.readValue(payload, LocationDTO.class);
            // 위치 처리 로직 - 비콘 기반 위치와 GPS 기반 위치 구분
            if (locationDTO.getPosition() != null && locationDTO.getTunnel() != null) {
                // 비콘 기반 위치 처리
                handleBeaconLocation(locationDTO);
            } else if (locationDTO.getLatitude() != null && locationDTO.getLongitude() != null) {
                // GPS 기반 위치 처리
                handleGPSLocation(locationDTO);
            }

            System.out.println("위치 정보 처리 완료: " + locationDTO);

        } catch (Exception e) {
            // 예외 처리 및 오류 로그
            System.err.println("WebSocket 메시지 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleBeaconLocation(LocationDTO locationDTO) {
        // 비콘 기반 위치 저장 로직
        Location location = Location.builder()
                .position(locationDTO.getPosition())
                .token(locationDTO.getToken())
                .tunnel(locationDTO.getTunnel())
                .build();

        if (locationRepository.existsLocationByToken(locationDTO.getToken())) {
            // 이미 존재하는 위치 정보 업데이트
            Location existingLocation = locationRepository.findLocationByToken(locationDTO.getToken());
            existingLocation.setPosition(locationDTO.getPosition());
            existingLocation.setLatitude(null);
            existingLocation.setLongitude(null);
            locationRepository.save(existingLocation);
        } else {
            // 새로운 위치 정보 저장
            locationRepository.save(location);
        }

        // DB에 저장된 비콘 기반 위치 정보 확인
        System.out.println("비콘 기반 위치 저장 - Position: " + locationDTO.getPosition() + ", Tunnel: " + locationDTO.getTunnel());
    }

    private void handleGPSLocation(LocationDTO locationDTO) {
        // GPS 기반 위치 저장 로직
        Location gpsLocation = Location.builder()
                .latitude(locationDTO.getLatitude())
                .longitude(locationDTO.getLongitude())
                .token(locationDTO.getToken())
                .build();

        // GPS는 터널 정보가 필요 없으므로 token만을 기준으로 저장
        if (locationRepository.existsLocationByToken(locationDTO.getToken())) {
            // 이미 존재하는 GPS 위치 정보 업데이트
            Location existingGPSLocation = locationRepository.findLocationByToken(locationDTO.getToken());
            existingGPSLocation.setLatitude(locationDTO.getLatitude());
            existingGPSLocation.setLongitude(locationDTO.getLongitude());
            existingGPSLocation.setPosition(null);
            existingGPSLocation.setTunnel(null);
            locationRepository.save(existingGPSLocation);
        } else {
            // 새로운 GPS 위치 정보 저장
            locationRepository.save(gpsLocation);
        }

        // DB에 저장된 GPS 기반 위치 정보 확인
        System.out.println("GPS 기반 위치 저장 - Latitude: " + locationDTO.getLatitude() + ", Longitude: " + locationDTO.getLongitude());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // WebSocket 연결이 종료될 때 실행되는 로직
        System.out.println("WebSocket 연결 종료: " + session.getId() + " 상태: " + status);
        locationRepository.deleteLocationByTokenAndTunnel(token, tunnel);
    }
}
