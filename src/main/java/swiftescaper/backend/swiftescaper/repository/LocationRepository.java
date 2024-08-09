package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swiftescaper.backend.swiftescaper.domain.Location;
import swiftescaper.backend.swiftescaper.domain.User;
import swiftescaper.backend.swiftescaper.domain.Tunnel;

public interface LocationRepository extends JpaRepository<Location, Long> {


    Boolean existsLocationByUserAndTunnel(User user, Tunnel tunnel);

    Location findLocationByUserAndTunnel(User user, Tunnel tunnel);
}
