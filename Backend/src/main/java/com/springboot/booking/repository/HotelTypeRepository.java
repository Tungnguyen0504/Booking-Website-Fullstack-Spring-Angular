package com.springboot.booking.repository;

import com.springboot.booking.model.entity.HotelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelTypeRepository extends JpaRepository<HotelType, Long> {
}
