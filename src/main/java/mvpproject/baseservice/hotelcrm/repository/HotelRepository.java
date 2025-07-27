package mvpproject.baseservice.hotelcrm.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import mvpproject.baseservice.hotelcrm.model.entity.AddressEntity;
import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long>, JpaSpecificationExecutor<HotelEntity> {

    default List<HotelEntity> searchHotels(String name, String brand, String city,
                                           String country, List<String> amenities) {
        return findAll(createSearchSpecification(name, brand, city, country, amenities));
    }

    private Specification<HotelEntity> createSearchSpecification(String name, String brand,
                                                                 String city, String country,
                                                                 List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                ));
            }

            if (brand != null && !brand.isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("brand")),
                        brand.toLowerCase()
                ));
            }

            if (city != null && !city.isBlank() || country != null && !country.isBlank()) {
                Join<HotelEntity, AddressEntity> addressJoin = root.join("address");

                if (city != null && !city.isBlank()) {
                    predicates.add(criteriaBuilder.equal(
                            criteriaBuilder.lower(addressJoin.get("city")),
                            city.toLowerCase()
                    ));
                }

                if (country != null && !country.isBlank()) {
                    predicates.add(criteriaBuilder.equal(
                            criteriaBuilder.lower(addressJoin.get("country")),
                            country.toLowerCase()
                    ));
                }
            }

            if (amenities != null && !amenities.isEmpty()) {
                Join<HotelEntity, String> amenitiesJoin = root.join("amenities");

                List<Predicate> amenityPredicates = new ArrayList<>();
                for (String amenity : amenities) {
                    amenityPredicates.add(criteriaBuilder.equal(
                            criteriaBuilder.lower(amenitiesJoin),
                            amenity.toLowerCase()
                    ));
                }

                predicates.add(criteriaBuilder.and(amenityPredicates.toArray(new Predicate[0])));
                assert query != null;
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Query("SELECT h.brand, COUNT(h) FROM HotelEntity h GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();

    @Query("SELECT h.address.city, COUNT(h) FROM HotelEntity h GROUP BY h.address.city")
    List<Object[]> countHotelsByCity();

    @Query("SELECT h.address.country, COUNT(h) FROM HotelEntity h GROUP BY h.address.country")
    List<Object[]> countHotelsByCountry();

    @Query("SELECT a, COUNT(h) FROM HotelEntity h JOIN h.amenities a GROUP BY a")
    List<Object[]> countHotelsByAmenity();
}
