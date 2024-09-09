package swiftescaper.backend.swiftescaper.service.accident;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swiftescaper.backend.swiftescaper.converter.AccidentConverter;
import swiftescaper.backend.swiftescaper.domain.entity.Accident;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.repository.AccidentRepository;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.service.fcm.FCMServiceImpl;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final LocationRepository locationRepository;
    private final AccidentRepository accidentRepository;

    private final AccidentConverter accidentConverter;

    private final FCMServiceImpl fcmService;

    @Override
    public Void controlAccident(AccidentRequestDto.AccidentDto accidentDto) {
        // 사고 정보를 DB에 저장
        Accident accident = accidentConverter.toAccident(accidentDto);
        accidentRepository.save(accident);

        // 범위 별로 유저의 위치 탐색 (0~100 미터 범위)
        List<Location> rangeList;

        if (accidentDto.getPosition() != null && accidentDto.getTunnel() != null) {
            // 비콘 기반 위치로 유저 검색
            rangeList = locationRepository.findLocationsWithinDistance(
                    accidentDto.getPosition(),   // 비콘 기반 위치 (터널 내 상대 위치)
                    accidentDto.getTunnel(),     // 터널 ID
                    0.0, 100.0);                 // 범위: 0 ~ 100 미터
        } else if (accidentDto.getLatitude() != null && accidentDto.getLongitude() != null) {
            // GPS 기반 위치로 유저 검색
            rangeList = locationRepository.findLocationsWithinGPSDistance(
                    accidentDto.getLatitude(),   // GPS 위도
                    accidentDto.getLongitude(),  // GPS 경도
                    0.0, 180.0);                 // 범위: 0 ~ 180
        } else {
            // 비콘과 GPS 데이터가 모두 없는 경우
            log.error("위치 정보가 없습니다. 사고 처리를 중단합니다.");
            return null;
        }

        // 범위 내 사용자 수 출력
        System.out.println("범위 내 사용자 수: " + rangeList.size());

        // 범위 내 유저들에게 FCM 알림 전송
        for (Location rangeUser : rangeList) {
            fcmService.sendNotification(
                    rangeUser.getToken(),
                    accidentDto.getTunnel(), // 터널 정보 전송
                    AccidentType.fromInt(accidentDto.getType()).toString()  // 사고 타입
            );
        }

        return null;
    }
}
