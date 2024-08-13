package swiftescaper.backend.swiftescaper.service.fcm;

public interface FCMService {
    Void sendNotification(String token, String title, String body);
}
