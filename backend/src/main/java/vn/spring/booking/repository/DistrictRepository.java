package vn.spring.booking.repository;

import vn.spring.booking.entities.District;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface DistrictRepository extends BaseRepository<District, UUID> {
    List<District> findByProvinceId(UUID provinceId);
}
