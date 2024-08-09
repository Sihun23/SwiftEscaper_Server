package swiftescaper.backend.swiftescaper.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swiftescaper.backend.swiftescaper.domain.Location;
import swiftescaper.backend.swiftescaper.domain.User;
import swiftescaper.backend.swiftescaper.domain.Tunnel;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.repository.UserRepository;
import swiftescaper.backend.swiftescaper.repository.TunnelRepository;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final TunnelRepository tunnelRepository;

    public void sendLocation(Double lat, Double lng, Long tunnelId, Long userId) {

        // userId로 User 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

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
        locationRepository.save(location);
    }
}
