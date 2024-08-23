package swiftescaper.backend.swiftescaper.web.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swiftescaper.backend.swiftescaper.apiPayload.ApiResponse;
import swiftescaper.backend.swiftescaper.apiPayload.code.status.SuccessStatus;
import swiftescaper.backend.swiftescaper.domain.entity.Location;
import swiftescaper.backend.swiftescaper.domain.entity.Tunnel;
import swiftescaper.backend.swiftescaper.repository.BeaconLocationRepository;
import swiftescaper.backend.swiftescaper.repository.LocationRepository;
import swiftescaper.backend.swiftescaper.repository.TunnelRepository;
import swiftescaper.backend.swiftescaper.service.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/")
    public String postLocations(@Parameter(description = "Position of the location", required = true) @RequestParam Double position,
                                   @Parameter(description = "tunnel", required = true) @RequestParam String tunnel,
                                   @Parameter(description = "Token of the user", required = true) @RequestParam String token) {

        Location location = Location.builder()
                .position(position)
                .token(token)
                .tunnel(tunnel)
                .build();

        locationRepository.save(location);

        return "위치 저장 성공";
    }

    @GetMapping("/")
    public ApiResponse<List<Location>> getLocations() {
        return ApiResponse.of(SuccessStatus.FIND_LOCATION_SUCCESS, locationRepository.findAll());
    }
}
