package com.springboot.booking.repository;

import com.springboot.booking.model.entity.SpecialAround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialAroundRepository extends JpaRepository<SpecialAround, Long> {

    SpecialAround findSpecialAroundByDescription(String description);
}
