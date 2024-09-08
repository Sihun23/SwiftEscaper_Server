package swiftescaper.backend.swiftescaper.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import swiftescaper.backend.swiftescaper.domain.common.DateBaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Location extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = true) // 비콘이 감지될 때만 사용
    private String tunnel;

    @Column(nullable = true) // 비콘 기반 상대적 위치
    private Double position;

    @Column(nullable = true) // GPS 절대적 위치 - 위도
    private Double latitude;

    @Column(nullable = true) // GPS 절대적 위치 - 경도
    private Double longitude;
}