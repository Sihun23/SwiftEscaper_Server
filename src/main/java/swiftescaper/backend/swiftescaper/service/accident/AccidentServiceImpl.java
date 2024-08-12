package swiftescaper.backend.swiftescaper.service.accident;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swiftescaper.backend.swiftescaper.converter.AccidentConverter;
import swiftescaper.backend.swiftescaper.domain.entity.Accident;
import swiftescaper.backend.swiftescaper.domain.entity.User;
import swiftescaper.backend.swiftescaper.repository.AccidentRepository;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.service.FCMService;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccidentServiceImpl implements AccidentService{

    private final LocationRepository locationRepository;
    private final AccidentRepository accidentRepository;

    private final AccidentConverter accidentConverter;

    private final FCMService fcmService;

    @Override
    public Void controlAccident(AccidentRequestDto.AccidentDto accidentDto) {
        //save Accident log in DB
        Accident accident= accidentConverter.toAccident(accidentDto);
        accidentRepository.save(accident);

        //범위 별로 유저의 위치 탐색 <- 주요 알고리즘 (0~100)
        List<User> rangeList = locationRepository.findLocationsWithinDistance(accident.getLat(),
                                                        accidentDto.getLng(),
                                                        0D, 100D);

        //범위 조절 해야함

        //transport FCM, 사고 정보, 터널, 유저 정보 전달
        rangeList.stream().map(rangeUser ->{
            fcmService.sendNotification(rangeUser.getToken(), accidentDto.getTunnel(), AccidentType.fromInt(accidentDto.getType()).toString());
            return null;
        });

        return null;
    }
}
