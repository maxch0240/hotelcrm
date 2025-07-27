package mvpproject.baseservice.hotelcrm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mvpproject.baseservice.hotelcrm.model.dto.HotelBriefDto;
import mvpproject.baseservice.hotelcrm.model.dto.HotelDto;
import mvpproject.baseservice.hotelcrm.service.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HotelService hotelService;

    private final HotelBriefDto hotelBrief = HotelBriefDto.builder()
            .id(1L)
            .name("Grand Plaza")
            .description("Luxury hotel")
            .address("123 Main St, New York")
            .phone("+1234567890")
            .build();

    private final HotelDto hotelDto = HotelDto.builder()
            .id(1L)
            .name("Grand Plaza")
            .description("Luxury hotel")
            .brand("Hilton")
            .amenities(List.of("Pool", "Spa"))
            .build();

    @Test
    void getHotelById_shouldReturnHotel() throws Exception {
        Mockito.when(hotelService.getHotelById(1L)).thenReturn(hotelDto);

        mockMvc.perform(get("/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Grand Plaza"));
    }

    @Test
    void getAllHotels_shouldReturnHotelList() throws Exception {
        Mockito.when(hotelService.getAll()).thenReturn(List.of(hotelBrief));

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Grand Plaza"));
    }

    @Test
    void searchHotels_shouldReturnFilteredResults() throws Exception {
        Mockito.when(hotelService.searchHotels("Grand", "Hilton", "New York", "USA", List.of("Pool")))
                .thenReturn(List.of(hotelBrief));

        mockMvc.perform(get("/search")
                        .param("name", "Grand")
                        .param("brand", "Hilton")
                        .param("city", "New York")
                        .param("country", "USA")
                        .param("amenities", "Pool"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Grand Plaza"));
    }

    @Test
    void createHotel_shouldReturnCreatedHotel() throws Exception {
        Mockito.when(hotelService.create(hotelDto)).thenReturn(hotelBrief);

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Grand Plaza"));
    }

    @Test
    void addAmenities_shouldReturnUpdatedAmenities() throws Exception {
        List<String> amenities = List.of("Pool", "Gym");
        List<String> updatedAmenities = List.of("Pool", "Spa", "Gym");

        Mockito.when(hotelService.addAmenities(1L, amenities))
                .thenReturn(updatedAmenities);

        mockMvc.perform(post("/hotels/1/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Pool"))
                .andExpect(jsonPath("$[1]").value("Spa"))
                .andExpect(jsonPath("$[2]").value("Gym"));
    }

    @Test
    void getHistogram_shouldReturnCounts() throws Exception {
        Map<String, Long> histogram = Map.of("Hilton", 5L, "Marriott", 3L);
        Mockito.when(hotelService.getHistogram("brand")).thenReturn(histogram);

        mockMvc.perform(get("/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Hilton").value(5))
                .andExpect(jsonPath("$.Marriott").value(3));
    }
}