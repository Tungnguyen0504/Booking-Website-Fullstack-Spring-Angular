package com.springboot.booking.repository;

import com.springboot.booking.entities.Accommodation;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface AccommodationRepository extends BaseRepository<Accommodation, UUID> {
    @Query(value = "SELECT a FROM Accommodation a WHERE LOWER(a.accommodationName) = LOWER(:accommodationName) AND a.status = 'ACTIVE'")
    List<Accommodation> getByAccommodationName(String accommodationName);

    @Query(value = "SELECT a FROM Accommodation a WHERE a.status = 'ACTIVE'")
    List<Accommodation> getAll();
}
