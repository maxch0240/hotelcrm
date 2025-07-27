package mvpproject.baseservice.hotelcrm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import mvpproject.baseservice.hotelcrm.model.mapper.HotelMapper;
import mvpproject.baseservice.hotelcrm.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelBriefDto create(HotelDto hotelDto) {
        HotelEntity entity = hotelMapper.convertToEntity(hotelDto);
        HotelEntity savedEntity = hotelRepository.save(entity);
        return hotelMapper.convertToBriefDto(savedEntity);
    }

    public HotelDto getHotelById(Long id) {
        Optional<HotelEntity> hotelEntity = hotelRepository.findById(id);
        HotelDto hotelDto;
        if (hotelEntity.isPresent()) {
            hotelDto = hotelMapper.convertToDto(hotelEntity.get());
        } else {
            return null;
        }

        return hotelDto;
    }

    public List<HotelBriefDto> getAll() {
        List<HotelEntity> hotelEntities = hotelRepository.findAll();
        List<HotelBriefDto> hotelBriefDtoList = new ArrayList<>();
        for (HotelEntity hotelEntity : hotelEntities) {
            hotelBriefDtoList.add(hotelMapper.convertToBriefDto(hotelEntity));
        }

        return hotelBriefDtoList;
    }

    public List<HotelBriefDto> searchHotels(String name, String brand, String city,
                                            String country, List<String> amenities) {
        List<HotelEntity> hotels = hotelRepository.searchHotels(
                name, brand, city, country, amenities
        );

        return hotels.stream()
                .map(hotelMapper::convertToBriefDto)
                .toList();
    }

    @Transactional
    public List<String> addAmenities(Long hotelId, List<String> amenities) {
        Optional<HotelEntity> hotel = hotelRepository.findById(hotelId);
        HotelEntity hotelEntity = hotel.orElse(null);

        assert hotelEntity != null;
        if (hotelEntity.getAmenities() == null) {
            hotelEntity.setAmenities(new ArrayList<>());
        }

        Set<String> existingAmenities = hotelEntity.getAmenities().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<String> newAmenities = amenities.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(amenity -> !amenity.isBlank())
                .filter(amenity -> !existingAmenities.contains(amenity.toLowerCase()))
                .toList();

        hotelEntity.getAmenities().addAll(newAmenities);

        hotelRepository.save(hotelEntity);
        return hotelEntity.getAmenities();
    }
}
