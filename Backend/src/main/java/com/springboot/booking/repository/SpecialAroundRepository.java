package com.springboot.booking.repository;

import com.springboot.booking.model.entity.SpecialAround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialAroundRepository extends JpaRepository<SpecialAround, Long> {

    Optional<SpecialAround> findSpecialAroundByDescription(String description);
}
