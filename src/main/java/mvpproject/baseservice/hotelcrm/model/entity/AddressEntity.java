package mvpproject.baseservice.hotelcrm.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AddressEntity {
    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
