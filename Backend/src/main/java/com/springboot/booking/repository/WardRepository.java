package com.springboot.booking.repository;

import com.springboot.booking.model.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {

    List<Ward> findByDistrictId(Long districtId);
}
