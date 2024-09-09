package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Boolean existsLocationByTokenAndTunnel(String token, String tunnel);

    Location findLocationByTokenAndTunnel(String token, String tunnel);

    Void deleteLocationByTokenAndTunnel(String token,String tunnel);

    @Query(value = "SELECT l.*, " +
            "abs(l.position - :position) AS distance " +
            "FROM location l " +
            "HAVING distance BETWEEN :minRadius AND :maxRadius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<Location> findLocationsWithinDistance(@Param("position") double position,
                                               @Param("minRadius") double minRadius,
                                               @Param("maxRadius") double maxRadius);

}
