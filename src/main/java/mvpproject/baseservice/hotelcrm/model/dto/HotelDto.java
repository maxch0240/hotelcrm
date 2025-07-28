package mvpproject.baseservice.hotelcrm.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для представления отеля")
public class HotelDto {
    @Schema(description = "Уникальный идентификатор отеля", example = "1")
    private Long id;
    @Schema(description = "Название отеля", example = "Grand Plaza")
    private String name;
    @Schema(description = "Описание отеля",
            example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious ...")
    private String description;
    @Schema(description = "Название бренда", example = "Hilton")
    private String brand;
    @Schema(description = "Адресс отеля",
            example = """
                    "houseNumber": 9
                    "street": "Pobediteley Avenue",
                    "city": "Minsk",
                    "country": "Belarus",
                    "postCode": "220004\"""")
    private AddressDto address;
    @Schema(description = "Контактные данные",
            example = "\"phone\": \"+375 17 309-80-00\",\n" +
                    "\"email\": \"doubletreeminsk.info@hilton.com\"")
    private ContactsDto contacts;
    @Schema(description = "Заселение и выселение из отеля",
            example = "\"checkIn\": \"14:00\",\n" +
                    "\"checkOut\": \"12:00\"")
    private ArrivalTimeDto arrivalTime;
    @Schema(description = "Дополнительные опции",
            example = """
                    ."Free parking",
                    "Free WiFi",
                    "Non-smoking rooms",
                    "Concierge",
                    "On-site restaurant\"""")
    private List<String> amenities;
}
