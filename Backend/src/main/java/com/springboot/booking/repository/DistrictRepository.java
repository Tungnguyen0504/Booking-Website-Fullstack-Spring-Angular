package com.springboot.booking.repository;

import com.springboot.booking.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByProvinceId(Long provinceId);
}
