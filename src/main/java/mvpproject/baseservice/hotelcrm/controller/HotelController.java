package mvpproject.baseservice.hotelcrm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Hotel Controller", description = "Контроллер для работы с отелями")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Информация по отелю", description = "Позволяет получить полную информацию")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        HotelDto hotelDto = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelDto);
    }

    @GetMapping("/hotels")
    @Operation(summary = "Информация по отелям", description = "Краткая информация по всем отелям")
    public ResponseEntity<List<HotelBriefDto>> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск по отелю", description = "Поиск отелей с краткой информацией по следующим параметрам: name, brand, city, country, amenities")
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
    @Operation(summary = "Создание нового отеля")
    public ResponseEntity<HotelBriefDto> create(@RequestBody HotelDto hotelDto) {
        return ResponseEntity.ok(hotelService.create(hotelDto));
    }

    @PostMapping("/hotels/{id}/amenities")
    @Operation(summary = "добавление списка amenities к отелю")
    public ResponseEntity<List<String>> addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        return ResponseEntity.ok(hotelService.addAmenities(id, amenities));
    }

    @GetMapping("/histogram/{param}")
    @Operation(summary = "получение колличества отелей сгруппированных по каждому значению указанного параметра. Параметр: brand, city, country, amenities")
    public ResponseEntity<Map<String, Long>> getHistogram(
            @PathVariable String param) {
        return ResponseEntity.ok(hotelService.getHistogram(param));
    }
}
