package mvpproject.baseservice.hotelcrm.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Embedded
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Embedded
    @JoinColumn(name = "contacts_id", referencedColumnName = "id")
    private ContactsEntity contacts;

    @Embedded
    @JoinColumn(name = "arrival_time_id", referencedColumnName = "id")
    private ArrivalTimeEntity arrivalTime;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;
}
