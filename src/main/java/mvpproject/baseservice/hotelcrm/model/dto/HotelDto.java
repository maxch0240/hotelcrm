package mvpproject.baseservice.hotelcrm.model.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class HotelDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactsDto contacts;
    private ArrivalTimeDto arrivalTime;
    private List<String> amenities;
}
