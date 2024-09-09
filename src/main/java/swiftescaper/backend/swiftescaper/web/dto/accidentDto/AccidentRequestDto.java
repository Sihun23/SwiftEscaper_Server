package swiftescaper.backend.swiftescaper.web.dto.accidentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccidentRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class AccidentDto {
        private String tunnel;      // 비콘 기반 터널 ID
        private Integer type;       // 사고 타입
        private Double position;    // 비콘 기반 상대적 위치
        private Double latitude;    // GPS 기반 위도
        private Double longitude;   // GPS 기반 경도
    }
}
