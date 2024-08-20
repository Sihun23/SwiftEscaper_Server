package swiftescaper.backend.swiftescaper.web.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;
import swiftescaper.backend.swiftescaper.repository.BeaconLocationRepository;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
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
    private TunnelRepository tunnelRepository;

    @Autowired
    private BeaconLocationRepository beaconLocationRepository;

    @PostMapping("/send111")
    public String sendNotification(@Parameter(description = "Latitude of the location", required = true) @RequestParam Double lat,
                                   @Parameter(description = "Longitude of the location", required = true) @RequestParam Double lng,
                                   @Parameter(description = "ID of the tunnel", required = true) @RequestParam Long tunnelId,
                                   @Parameter(description = "ID of the user", required = true) @RequestParam String token) {

        // 사용자와 터널을 ID로 조회
        Tunnel tunnel = tunnelRepository.findById(tunnelId).orElseThrow(() -> new IllegalArgumentException("Invalid tunnel ID"));

        // 사용자와 터널을 기준으로 Location 조회
        if (locationRepository.existsLocationByTokenAndTunnel(token, tunnel)) {
            Location location = locationRepository.findLocationByTokenAndTunnel(token, tunnel);
            location.setLat(lat);
            location.setLng(lng);
            locationRepository.save(location);
        } else {
            locationService.sendLocation(lat, lng, tunnelId, token);
        }
        return "Notification sent successfully!";
    }
}
