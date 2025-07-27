package mvpproject.baseservice.hotelcrm.repository;

import mvpproject.baseservice.hotelcrm.model.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
