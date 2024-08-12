package swiftescaper.backend.swiftescaper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.User;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.repository.UserRepository;
import swiftescaper.backend.swiftescaper.repository.TunnelRepository;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository accidentRepository;
    private final UserRepository userRepository;
    private final TunnelRepository tunnelRepository;

    public void sendLocation(Double lat, Double lng, Long tunnelId, Long userId) {
        // tunnelId로 Tunnel 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tunnel ID: " + tunnelId));
        // tunnelId로 Tunnel 가져오기
        Tunnel tunnel = tunnelRepository.findById(tunnelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tunnel ID: " + tunnelId));

        Location location = Location.builder()
                .lat(lat)
                .lng(lng)
                .tunnel(tunnel)
                .user(user)
                .build();

        // Location 저장
        accidentRepository.save(location);
    }
}
