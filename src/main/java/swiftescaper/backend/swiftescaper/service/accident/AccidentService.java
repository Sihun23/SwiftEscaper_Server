package swiftescaper.backend.swiftescaper.service.accident;

import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;

public interface AccidentService {
    Void controlAccident(AccidentRequestDto.AccidentDto accidentDto);
}
