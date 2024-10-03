package swiftescaper.backend.swiftescaper.apiPayload.code;

import lombok.Data;

@Data
public class LocationDTO {

    private String token;    // FCM 토큰 (장치 식별자)

    private String tunnel;   // 터널 ID (비콘이 감지된 경우)

    private Double position; // 비콘을 통해 받은 상대적 위치 (터널 내)

    private Double latitude;  // GPS 위도

    private Double longitude; // GPS 경도
}
