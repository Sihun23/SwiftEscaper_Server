package swiftescaper.backend.swiftescaper.converter;

import org.springframework.stereotype.Component;
import swiftescaper.backend.swiftescaper.domain.entity.Accident;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentType;

@Component
public class AccidentConverter {
    public Accident toAccident(AccidentRequestDto.AccidentDto accidentDto){
        return Accident.builder()
                .type(AccidentType.fromInt(accidentDto.getType()))
                .position(accidentDto.getPosition())
                .tunnel(accidentDto.getTunnel())
                .build();
    }
}
