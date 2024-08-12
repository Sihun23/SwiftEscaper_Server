package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.User;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {


    Boolean existsLocationByUserAndTunnel(User user, Tunnel tunnel);

    Location findLocationByUserAndTunnel(User user, Tunnel tunnel);

    @Query(value = "SELECT u.*, " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(u.latitude)) * cos(radians(u.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(u.latitude)))) AS distance " +
            "FROM users u " +
            "HAVING distance BETWEEN :minRadius AND :maxRadius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<User> findLocationsWithinDistance(@Param("latitude") double latitude,
                                       @Param("longitude") double longitude,
                                       @Param("minRadius") double minRadius,
                                       @Param("maxRadius") double maxRadius);

}
