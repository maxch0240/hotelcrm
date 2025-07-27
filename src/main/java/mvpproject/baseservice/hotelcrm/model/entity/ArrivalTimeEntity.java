package mvpproject.baseservice.hotelcrm.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ArrivalTimeEntity {
    private String checkIn;
    private String checkOut;
}
