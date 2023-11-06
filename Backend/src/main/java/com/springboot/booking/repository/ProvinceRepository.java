package com.springboot.booking.repository;

import com.springboot.booking.model.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query(value = "SELECT * FROM booking_management_system.province ORDER BY province_id LIMIT :range", nativeQuery = true)
    List<Province> findTopProvinceOrderById(@Param("range") int range);
}
