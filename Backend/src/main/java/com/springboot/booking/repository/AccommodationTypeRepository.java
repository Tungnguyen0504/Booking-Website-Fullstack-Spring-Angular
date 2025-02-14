package com.springboot.booking.repository;

import com.springboot.booking.entities.AccommodationType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface AccommodationTypeRepository extends BaseRepository<AccommodationType, UUID> {
    @Query(value = "select at from AccommodationType at where at.status = 'ACTIVE'")
    List<AccommodationType> getAll();
}
