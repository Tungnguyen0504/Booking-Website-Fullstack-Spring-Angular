package vn.spring.booking.repository;

import vn.spring.booking.entities.Province;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface ProvinceRepository extends BaseRepository<Province, UUID> {
  @Query(
      value = "SELECT * FROM booking_management_system.province ORDER BY province_id LIMIT :range",
      nativeQuery = true)
  List<Province> findTopProvinceOrderById(@Param("range") int range);
}
