package com.springboot.booking.repository;

import com.springboot.booking.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, JpaSpecificationExecutor<Accommodation> {

    @Query(value = "SELECT a FROM Accommodation a WHERE LOWER(a.accommodationName) = LOWER(:accommodationName) AND a.status = 'ACTIVE'")
    List<Accommodation> getByAccommodationName(String accommodationName);

    @Query(value = "SELECT a FROM Accommodation a WHERE a.status = 'ACTIVE'")
    List<Accommodation> getAll();
}
