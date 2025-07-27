package mvpproject.baseservice.hotelcrm.controller;

import lombok.RequiredArgsConstructor;
import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
