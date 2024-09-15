package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swiftescaper.backend.swiftescaper.domain.entity.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    // 비콘 데이터를 기반으로 위치를 찾기 위한 메소드
    Boolean existsLocationByToken(String token);

    Location findLocationByToken(String token);

    Void deleteLocationByTokenAndTunnel(String token,String tunnel);

    @Query(value = "SELECT l.*, " +
            "l.position - :position AS distance " +
            "FROM location l " +
            "WHERE l.tunnel = :tunnel " +
            "AND (l.position - :position) BETWEEN :minRadius AND :maxRadius " +
            "ORDER BY abs(l.position - :position)",
            nativeQuery = true)
    List<Location> findLocationsWithinDistance(@Param("position") double position,
                                               @Param("tunnel") String tunnel,
                                               @Param("minRadius") double minRadius,
                                               @Param("maxRadius") double maxRadius);

    // GPS 기반 위치 조회
    @Query(value = "SELECT l.*, " +
            "ST_Distance_Sphere(POINT(l.longitude, l.latitude), POINT(:longitude, :latitude)) AS distance " +
            "FROM location l " +
            "WHERE l.longitude BETWEEN :longitude - :longRadius AND :longitude + :longRadius " +
            "AND l.latitude BETWEEN :latitude - :latRadius AND :latitude + :latRadius " +
            "AND ST_Distance_Sphere(POINT(l.longitude, l.latitude), POINT(:longitude, :latitude)) BETWEEN :minRadius AND :maxRadius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<Location> findLocationsWithinGPSDistance(@Param("latitude") double latitude,
                                                  @Param("longitude") double longitude,
                                                  @Param("minRadius") double minRadius,
                                                  @Param("maxRadius") double maxRadius);

}
