package com.springboot.booking.repository;

import com.springboot.booking.model.entity.PropertyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyConfigRepository extends JpaRepository<PropertyConfig, Long> {
}
