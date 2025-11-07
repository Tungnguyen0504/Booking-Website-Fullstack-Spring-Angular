package vn.spring.booking.repository;

import vn.spring.booking.entities.AccommodationType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface AccommodationTypeRepository extends BaseRepository<AccommodationType, UUID> {
    @Query(value = "select at from AccommodationType at where at.status = 'ACTIVE'")
    List<AccommodationType> getAll();
}
