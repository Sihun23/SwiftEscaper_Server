package swiftescaper.backend.swiftescaper.service.fcm;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import swiftescaper.backend.swiftescaper.service.accident.AccidentHandler;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentRequestDto;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentSize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FCMServiceImpl implements FCMService{
    @Override
    public Void sendBatchNotification(List<String> tokens,
                                      AccidentRequestDto.AccidentDto accidentDto,
                                      LocalDateTime localDateTime,
                                      Boolean isTunnel,
                                      Integer distance) {
        MulticastMessage message = AccidentHandler.handleAccident(accidentDto, localDateTime, tokens, isTunnel,distance);
        try {
            if(message != null) {
                // 여러 기기로 한 번에 메시지 전송
                BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
                System.out.println("Successfully sent message: " + response.getSuccessCount() + " messages were sent successfully");

                if (response.getFailureCount() > 0) {
                    System.out.println(response.getFailureCount() + " messages failed to send.");
                    response.getResponses().forEach(sendResponse -> {
                        if (!sendResponse.isSuccessful()) {
                            System.err.println("Error sending message: " + sendResponse.getException().getMessage());
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Error sending FCM batch message: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
