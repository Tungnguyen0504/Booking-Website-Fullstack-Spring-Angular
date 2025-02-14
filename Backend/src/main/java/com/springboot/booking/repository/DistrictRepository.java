package com.springboot.booking.repository;

import com.springboot.booking.entities.District;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface DistrictRepository extends BaseRepository<District, UUID> {
    List<District> findByProvinceId(UUID provinceId);
}
