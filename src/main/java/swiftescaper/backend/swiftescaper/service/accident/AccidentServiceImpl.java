package swiftescaper.backend.swiftescaper.service.accident;

import com.mysql.cj.log.Log;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        /**
         * 비콘, GPS 좌표로 범위 내 유저 필터링
         */
        //사고 근처
        List<Location> rangeList200 = new ArrayList<>(locationRepository.findLocationsWithinDistance(
                accidentDto.getPosition(),
                accidentDto.getTunnel(),
                0.0, 200.0));

        //뒤만
        List<Location> rangeList400 = new ArrayList<>(locationRepository.findLocationsWithinDistance(
                accidentDto.getPosition(),
                accidentDto.getTunnel(),
                200.0, 400.0));

        //뒤만
        List<Location> rangeListTunnel = new ArrayList<>(locationRepository.findLocationsWithinDistance(
                accidentDto.getPosition(),
                accidentDto.getTunnel(),
                400.0, 10000.0));

        //GPS 전체
        List<Location> rangeListGPS = new ArrayList<>(locationRepository.findLocationsWithinGPSDistance(
                accidentDto.getLatitude(),   // GPS 위도
                accidentDto.getLongitude(),  // GPS 경도
                0.0, 1000));           //수정해야함

        if(!rangeList200.isEmpty())sendFCM(rangeList200, accidentDto,accident.getCreatedAt(), true,200);
        if(!rangeList400.isEmpty()) sendFCM(rangeList400, accidentDto,accident.getCreatedAt(), true, 400);
        if(!rangeListTunnel.isEmpty()) sendFCM(rangeListTunnel, accidentDto,accident.getCreatedAt(), true, 1000);
        if(!rangeListGPS.isEmpty()) sendFCM(rangeListGPS, accidentDto,accident.getCreatedAt(), false, 0);

        return null;
    }

    @Override
    public List<Accident> getAccidnet() {
        return accidentRepository.findAll();
    }

    public void sendFCM(List<Location> locations,
                        AccidentRequestDto.AccidentDto accidentDto,
                        LocalDateTime localDateTime,
                        Boolean isTunnel,
                        Integer distance) {
        List<String> tokens = locations.stream()
                .map(Location::getToken)
                .collect(Collectors.toList());
        fcmService.sendBatchNotification(tokens, accidentDto, localDateTime , isTunnel,distance);
    }
}
