package swiftescaper.backend.swiftescaper.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swiftescaper.backend.swiftescaper.domain.Location;
import swiftescaper.backend.swiftescaper.domain.User;
import swiftescaper.backend.swiftescaper.domain.Tunnel;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.repository.UserRepository;
import swiftescaper.backend.swiftescaper.repository.TunnelRepository;
import swiftescaper.backend.swiftescaper.service.LocationService;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TunnelRepository tunnelRepository;

    @PostMapping("/send")
    public String sendNotification(@Parameter(description = "Latitude of the location", required = true) @RequestParam Double lat,
                                   @Parameter(description = "Longitude of the location", required = true) @RequestParam Double lng,
                                   @Parameter(description = "ID of the tunnel", required = true) @RequestParam Long tunnelId,
                                   @Parameter(description = "ID of the user", required = true) @RequestParam Long userId) {

        // 사용자와 터널을 ID로 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Tunnel tunnel = tunnelRepository.findById(tunnelId).orElseThrow(() -> new IllegalArgumentException("Invalid tunnel ID"));

        // 사용자와 터널을 기준으로 Location 조회
        if (locationRepository.existsLocationByUserAndTunnel(user, tunnel)) {
            Location location = locationRepository.findLocationByUserAndTunnel(user, tunnel);
            location.setLat(lat);
            location.setLng(lng);
            locationRepository.save(location);
        } else {
            locationService.sendLocation(lat, lng, tunnelId, userId);
        }
        return "Notification sent successfully!";
    }
}
