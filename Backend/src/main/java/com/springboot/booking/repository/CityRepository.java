package com.springboot.booking.repository;

import com.springboot.booking.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT * FROM booking_management_system.city ORDER BY city_id LIMIT :range", nativeQuery = true)
    List<City> findTopCityOrderById(@Param("range") int range);
}
