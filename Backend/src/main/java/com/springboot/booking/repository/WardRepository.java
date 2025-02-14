package com.springboot.booking.repository;

import com.springboot.booking.entities.Ward;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface WardRepository extends BaseRepository<Ward, UUID> {
    List<Ward> findByDistrictId(UUID districtId);
}
