package mvpproject.baseservice.hotelcrm.service;

import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import mvpproject.baseservice.hotelcrm.model.mapper.HotelMapper;
import mvpproject.baseservice.hotelcrm.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    private final HotelEntity hotelEntity = HotelEntity.builder()
            .id(1L)
            .name("Grand Plaza")
            .brand("Hilton")
            .amenities(new ArrayList<>(List.of("Pool", "WiFi")))
            .build();

    private final HotelDto hotelDto = HotelDto.builder()
            .id(1L)
            .name("Grand Plaza")
            .brand("Hilton")
            .build();

    private final HotelBriefDto hotelBriefDto = HotelBriefDto.builder()
            .id(1L)
            .name("Grand Plaza")
            .build();

    @Test
    void createHotel_shouldReturnBriefDto() {
        when(hotelMapper.convertToEntity(hotelDto)).thenReturn(hotelEntity);
        when(hotelRepository.save(hotelEntity)).thenReturn(hotelEntity);
        when(hotelMapper.convertToBriefDto(hotelEntity)).thenReturn(hotelBriefDto);

        HotelBriefDto result = hotelService.create(hotelDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Grand Plaza");

        verify(hotelMapper).convertToEntity(hotelDto);
        verify(hotelRepository).save(hotelEntity);
        verify(hotelMapper).convertToBriefDto(hotelEntity);
    }

    @Test
    void getHotelById_shouldReturnDtoWhenExists() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelEntity));
        when(hotelMapper.convertToDto(hotelEntity)).thenReturn(hotelDto);

        HotelDto result = hotelService.getHotelById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Grand Plaza");
    }

    @Test
    void getHotelById_shouldReturnNullWhenNotExists() {
        when(hotelRepository.findById(2L)).thenReturn(Optional.empty());

        HotelDto result = hotelService.getHotelById(2L);

        assertThat(result).isNull();
    }

    @Test
    void getAll_shouldReturnBriefDtoList() {
        List<HotelEntity> entities = List.of(hotelEntity);
        when(hotelRepository.findAll()).thenReturn(entities);
        when(hotelMapper.convertToBriefDto(hotelEntity)).thenReturn(hotelBriefDto);

        List<HotelBriefDto> result = hotelService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Grand Plaza");
    }

    @Test
    void searchHotels_shouldReturnFilteredResults() {
        List<HotelEntity> entities = List.of(hotelEntity);
        when(hotelRepository.searchHotels("Grand", "Hilton", "Minsk", "Belarus", List.of("Pool")))
                .thenReturn(entities);
        when(hotelMapper.convertToBriefDto(hotelEntity)).thenReturn(hotelBriefDto);

        List<HotelBriefDto> result = hotelService.searchHotels(
                "Grand", "Hilton", "Minsk", "Belarus", List.of("Pool")
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void addAmenities_shouldAddUniqueAmenities() {
        List<String> newAmenities = List.of("Pool", "Spa", "   Gym   ", "");
        List<String> expectedAmenities = List.of("Pool", "WiFi", "Spa", "Gym");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelEntity));
        when(hotelRepository.save(any())).thenReturn(hotelEntity);

        List<String> result = hotelService.addAmenities(1L, newAmenities);

        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedAmenities);
        assertThat(hotelEntity.getAmenities()).containsExactly("Pool", "WiFi", "Spa", "Gym");

        verify(hotelRepository).save(hotelEntity);
    }

    @Test
    void addAmenities_shouldInitializeListWhenNull() {
        HotelEntity entityWithoutAmenities = HotelEntity.builder()
                .id(2L)
                .amenities(null)
                .build();

        when(hotelRepository.findById(2L)).thenReturn(Optional.of(entityWithoutAmenities));
        when(hotelRepository.save(any())).thenReturn(entityWithoutAmenities);

        List<String> result = hotelService.addAmenities(2L, List.of("Pool"));

        assertThat(result).containsExactly("Pool");
        assertThat(entityWithoutAmenities.getAmenities()).containsExactly("Pool");
    }

    @Test
    void getHistogramForBrand_shouldReturnCounts() {
        List<Object[]> brandCounts = List.of(
                new Object[]{"Hilton", 5L},
                new Object[]{"Marriott", 3L}
        );

        when(hotelRepository.countHotelsByBrand()).thenReturn(brandCounts);

        Map<String, Long> result = hotelService.getHistogram("brand");

        assertThat(result)
                .hasSize(2)
                .containsEntry("Hilton", 5L)
                .containsEntry("Marriott", 3L);
    }

    @Test
    void getHistogramForAmenities_shouldReturnCounts() {
        List<Object[]> amenityCounts = List.of(
                new Object[]{"Pool", 10L},
                new Object[]{"WiFi", 15L}
        );

        when(hotelRepository.countHotelsByAmenity()).thenReturn(amenityCounts);

        Map<String, Long> result = hotelService.getHistogram("amenities");

        assertThat(result)
                .hasSize(2)
                .containsEntry("Pool", 10L)
                .containsEntry("WiFi", 15L);
    }

    @Test
    void getHistogram_shouldThrowExceptionForInvalidParam() {
        assertThatThrownBy(() -> hotelService.getHistogram("invalid"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid histogram parameter: invalid");
    }
}