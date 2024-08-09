package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swiftescaper.backend.swiftescaper.domain.Tunnel;

public interface TunnelRepository extends JpaRepository<Tunnel, Long> {
    // Query Method 추가
}
