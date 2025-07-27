package mvpproject.baseservice.hotelcrm.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ContactsEntity {
    private String phone;
    private String email;
}
