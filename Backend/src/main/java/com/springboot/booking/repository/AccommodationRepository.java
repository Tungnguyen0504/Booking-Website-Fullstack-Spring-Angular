package com.springboot.booking.repository;

import com.springboot.booking.model.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query(value = "SELECT a FROM Accommodation a WHERE LOWER(a.accommodationName) = LOWER(:accommodationName)")
    List<Accommodation> findByAccommodationName(String accommodationName);

    List<Accommodation> findByStatus(String status);
}
