package swiftescaper.backend.swiftescaper.service.fcm;

import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentSize;

import java.time.LocalDateTime;
import java.util.List;

public interface FCMService {
    Void sendBatchNotification(List<String> tokens,
                               AccidentRequestDto.AccidentDto accidentDto,
                               LocalDateTime localDateTime,
                               Boolean isTunnel,
                               Integer distance);
}
