package swiftescaper.backend.swiftescaper.service.fcm;

import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentSize;

public interface FCMService {
    Void sendNotification(String token, String title, String body, AccidentSize accidentSize);
}
