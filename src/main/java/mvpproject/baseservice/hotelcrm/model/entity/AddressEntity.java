package mvpproject.baseservice.hotelcrm.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AddressEntity {
    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
