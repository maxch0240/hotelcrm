package mvpproject.baseservice.hotelcrm.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelBriefDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
