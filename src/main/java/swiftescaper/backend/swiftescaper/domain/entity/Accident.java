package swiftescaper.backend.swiftescaper.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import swiftescaper.backend.swiftescaper.domain.common.DateBaseEntity;
import swiftescaper.backend.swiftescaper.web.dto.accidentDto.AccidentType;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accident extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private AccidentType type;

    @Column(nullable = false)
    private String tunnel;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;
}
