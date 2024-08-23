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

    @Column(nullable = false)
    private String tunnel;

    @Column(nullable = false)
    private Double position;
}
