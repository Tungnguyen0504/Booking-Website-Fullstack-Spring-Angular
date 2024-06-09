package com.springboot.booking.repository;

import com.springboot.booking.model.entity.PropertyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyConfigRepository extends JpaRepository<PropertyConfig, Long> {
    @Query(value = "select pc.description from PropertyConfig pc where pc.property = :property group by pc.description")
    List<String> findByPropertyDistinct(String property);

    List<PropertyConfig> findByPropertyAndDescription(String property, String description);
}
