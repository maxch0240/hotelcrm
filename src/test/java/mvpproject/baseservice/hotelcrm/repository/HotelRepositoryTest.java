package mvpproject.baseservice.hotelcrm.repository;

import mvpproject.baseservice.hotelcrm.model.entity.AddressEntity;
import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class HotelRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HotelRepository hotelRepository;

    private HotelEntity createHotel(String name, String brand, String city,
                                    String country, String... amenities) {
        AddressEntity address = AddressEntity.builder()
                .city(city)
                .country(country)
                .build();

        return HotelEntity.builder()
                .name(name)
                .brand(brand)
                .address(address)
                .amenities(List.of(amenities))
                .build();
    }

    @Test
    void searchByName_shouldReturnMatchingHotels() {
        entityManager.persist(createHotel("Grand Plaza", "Hilton", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Plaza Hotel", "Marriott", "Moscow", "Russia"));
        entityManager.flush();

        List<HotelEntity> results = hotelRepository.searchHotels("plaza", null, null, null, null);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(HotelEntity::getName)
                .containsExactlyInAnyOrder("Grand Plaza", "Plaza Hotel");
    }

    @Test
    void searchByBrand_shouldReturnExactMatch() {
        entityManager.persist(createHotel("Hilton Minsk", "Hilton", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Marriott Moscow", "Marriott", "Moscow", "Russia"));
        entityManager.flush();

        List<HotelEntity> results = hotelRepository.searchHotels(null, "hilton", null, null, null);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getBrand()).isEqualTo("Hilton");
    }

    @Test
    void searchByCity_shouldReturnHotelsInSpecifiedCity() {
        entityManager.persist(createHotel("Hotel A", "Brand A", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Hotel B", "Brand B", "Moscow", "Russia"));
        entityManager.flush();

        List<HotelEntity> results = hotelRepository.searchHotels(null, null, "Minsk", null, null);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAddress().getCity()).isEqualTo("Minsk");
    }

    @Test
    void searchByCountry_shouldReturnHotelsInSpecifiedCountry() {
        entityManager.persist(createHotel("Hotel A", "Brand A", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Hotel B", "Brand B", "Moscow", "Russia"));
        entityManager.flush();

        List<HotelEntity> results = hotelRepository.searchHotels(null, null, null, "Belarus", null);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAddress().getCountry()).isEqualTo("Belarus");
    }


    @Test
    void searchByMultipleParams_shouldCombineConditions() {
        entityManager.persist(createHotel("Grand Plaza", "Hilton", "Minsk", "Belarus", "Pool"));
        entityManager.persist(createHotel("Plaza Royal", "Hilton", "Moscow", "Russia", "Spa"));
        entityManager.persist(createHotel("City Plaza", "Marriott", "Minsk", "Belarus", "Pool"));
        entityManager.flush();

        List<HotelEntity> results = hotelRepository.searchHotels(
                "plaza", "hilton", "minsk", "belarus", List.of("pool"));

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Grand Plaza");
    }

    @Test
    void searchShouldBeCaseInsensitive() {
        entityManager.persist(createHotel("Grand Plaza", "Hilton", "Minsk", "Belarus"));
        entityManager.flush();

        List<HotelEntity> nameResults = hotelRepository.searchHotels("GRAND plaza", null, null, null, null);
        List<HotelEntity> brandResults = hotelRepository.searchHotels(null, "HILTON", null, null, null);
        List<HotelEntity> cityResults = hotelRepository.searchHotels(null, null, "MINSK", null, null);
        List<HotelEntity> amenityResults = hotelRepository.searchHotels(null, null, null, null, List.of("POOL", "WIFI"));

        assertThat(nameResults).hasSize(1);
        assertThat(brandResults).hasSize(1);
        assertThat(cityResults).hasSize(1);
        assertThat(amenityResults).isEmpty();
    }

    @Test
    void countHotelsByBrand_shouldReturnCorrectCounts() {
        entityManager.persist(createHotel("Hotel A", "Hilton", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Hotel B", "Hilton", "Moscow", "Russia"));
        entityManager.persist(createHotel("Hotel C", "Marriott", "Minsk", "Belarus"));
        entityManager.flush();

        List<Object[]> results = hotelRepository.countHotelsByBrand();

        assertThat(results).hasSize(2);
        assertThat(results).contains(
                new Object[]{"Hilton", 2L},
                new Object[]{"Marriott", 1L}
        );
    }

    @Test
    void countHotelsByCity_shouldReturnCorrectCounts() {
        entityManager.persist(createHotel("Hotel A", "Hilton", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Hotel B", "Hilton", "Moscow", "Russia"));
        entityManager.persist(createHotel("Hotel C", "Marriott", "Minsk", "Belarus"));
        entityManager.flush();

        List<Object[]> results = hotelRepository.countHotelsByCity();

        assertThat(results).hasSize(2);
        assertThat(results).contains(
                new Object[]{"Minsk", 2L},
                new Object[]{"Moscow", 1L}
        );
    }

    @Test
    void countHotelsByCountry_shouldReturnCorrectCounts() {
        entityManager.persist(createHotel("Hotel A", "Hilton", "Minsk", "Belarus"));
        entityManager.persist(createHotel("Hotel B", "Hilton", "Moscow", "Russia"));
        entityManager.persist(createHotel("Hotel C", "Marriott", "Minsk", "Belarus"));
        entityManager.flush();

        List<Object[]> results = hotelRepository.countHotelsByCountry();

        assertThat(results).hasSize(2);
        assertThat(results).contains(
                new Object[]{"Belarus", 2L},
                new Object[]{"Russia", 1L}
        );
    }
}