package swiftescaper.backend.swiftescaper.domain;

import jakarta.persistence.*;
import lombok.*;
import swiftescaper.backend.swiftescaper.domain.common.DateBaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BeaconLocation extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long beaconId;

    @ManyToOne
    @JoinColumn(name = "tunnelId", nullable = false)  // 터널 ID를 참조
    private Tunnel tunnel;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;
}
