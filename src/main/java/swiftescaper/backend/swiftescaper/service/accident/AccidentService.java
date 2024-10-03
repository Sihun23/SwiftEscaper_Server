package swiftescaper.backend.swiftescaper.service.accident;

import swiftescaper.backend.swiftescaper.domain.entity.Accident;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;

import java.util.List;

public interface AccidentService {
    Void controlAccident(AccidentRequestDto.AccidentDto accidentDto);

    List<Accident> getAccidnet();
}
