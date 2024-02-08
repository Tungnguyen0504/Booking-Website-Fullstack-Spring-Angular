package com.springboot.booking.repository;

import com.springboot.booking.model.entity.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {

    @Query(value = "select at from AccommodationType at where at.status = 'ACTIVE'")
    List<AccommodationType> getAll();
}
