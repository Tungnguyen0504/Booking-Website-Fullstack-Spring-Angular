package vn.spring.booking.repository;

import vn.spring.booking.entities.Ward;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface WardRepository extends BaseRepository<Ward, UUID> {
    List<Ward> findByDistrictId(UUID districtId);
}
