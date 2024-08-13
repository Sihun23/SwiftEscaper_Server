package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Boolean existsLocationByTokenAndTunnel(String token, Tunnel tunnel);

    Location findLocationByTokenAndTunnel(String token, Tunnel tunnel);

    @Query(value = "SELECT l.*, " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(l.lat)) * cos(radians(l.lng) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(l.lat)))) AS distance " +
            "FROM location l " +
            "HAVING distance BETWEEN :minRadius AND :maxRadius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<Location> findLocationsWithinDistance(@Param("latitude") double latitude,
                                       @Param("longitude") double longitude,
                                       @Param("minRadius") double minRadius,
                                       @Param("maxRadius") double maxRadius);

}
