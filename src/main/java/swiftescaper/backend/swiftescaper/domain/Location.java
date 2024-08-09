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
public class Location extends DateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tunnel_id", nullable = false)
    private Tunnel tunnel;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;
}
