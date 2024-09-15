package swiftescaper.backend.swiftescaper.service.fcm;

import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentSize;

import java.util.List;

public interface FCMService {
    Void sendBatchNotification(List<String> tokens, String title, String body, AccidentSize accidentSize);
}
