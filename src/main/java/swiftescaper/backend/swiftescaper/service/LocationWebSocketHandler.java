package swiftescaper.backend.swiftescaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.repository.TunnelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@Component
public class LocationWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TunnelRepository tunnelRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Test : WebSocket 연결 성공여부
        System.out.println("WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 받은 메시지 JSON(lat, lng, tunnelId, token)
        String payload = message.getPayload();
        System.out.println("Received: " + payload);

        // JSON -> Map
        Map<String, Object> locationData = objectMapper.readValue(payload, Map.class);

        // lat, lng, tunnelId, token 추출
        Double lat = (Double) locationData.get("lat");
        Double lng = (Double) locationData.get("lng");
        Long tunnelId = ((Number) locationData.get("tunnelId")).longValue();
        String token = (String) locationData.get("fcmToken");

        // Tunnel 엔티티 가져오기
        Tunnel tunnel = tunnelRepository.findTunnelById(tunnelId);

        // Location 엔티티 생성 및 데이터베이스에 저장
        Location location = Location.builder()
                .lat(lat)
                .lng(lng)
                .token(token)
                .tunnel(tunnel)
                .build();

        if (locationRepository.existsLocationByTokenAndTunnel(token, tunnel)) {
            Location location1 = locationRepository.findLocationByTokenAndTunnel(token, tunnel);
            location1.setLat(lat);
            location1.setLng(lng);
            locationRepository.save(location1);
        } else {
            locationRepository.save(location);
        }

        // Test : DB 저장 확인
        System.out.println("데이터베이스에 저장된 위치 정보 - X: " + lat + ", Y: " + lng + ", TunnelId: " + tunnelId + ", Token: " + token);
    }
}
