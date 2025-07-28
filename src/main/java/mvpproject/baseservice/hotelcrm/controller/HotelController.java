package mvpproject.baseservice.hotelcrm.controller;

import lombok.RequiredArgsConstructor;
import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        HotelDto hotelDto = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelDto);
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelBriefDto>> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelBriefDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {

        List<HotelBriefDto> results = hotelService.searchHotels(
                name, brand, city, country, amenities
        );

        return ResponseEntity.ok(results);
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelBriefDto> create(@RequestBody HotelDto hotelDto) {
        return ResponseEntity.ok(hotelService.create(hotelDto));
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<List<String>> addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        return ResponseEntity.ok(hotelService.addAmenities(id, amenities));
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(
            @PathVariable String param) {
        return ResponseEntity.ok(hotelService.getHistogram(param));
    }
}
