package swiftescaper.backend.swiftescaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swiftescaper.backend.swiftescaper.domain.entity.Accident;
import swiftescaper.backend.swiftescaper.domain.entity.BeaconLocation;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;

import java.util.List;

public interface AccidentRepository extends JpaRepository<Accident, Long> {
}
