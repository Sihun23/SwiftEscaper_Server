package swiftescaper.backend.swiftescaper.web.dto.accidentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccidentRequestDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccidentDto {
        private String tunnel;
        private Integer type;
        private Double lat;
        private Double lng;
    }
}
