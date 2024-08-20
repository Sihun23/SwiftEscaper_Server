package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swiftescaper.backend.swiftescaper.domain.entity.BeaconLocation;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;

import java.util.List;

public interface BeaconLocationRepository extends JpaRepository<BeaconLocation, Long> {

    // 특정 Tunnel에 속한 모든 BeaconLocation을 찾는 메서드
    List<BeaconLocation> findByTunnel(Tunnel tunnel);

    // 특정 터널과 특정 위도 및 경도를 가진 비콘 위치를 찾는 메서드
    BeaconLocation findByTunnelAndLatAndLng(Tunnel tunnel, Double lat, Double lng);

}
