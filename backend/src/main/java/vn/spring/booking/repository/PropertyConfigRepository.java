package vn.spring.booking.repository;

import vn.spring.booking.entities.PropertyConfig;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface PropertyConfigRepository extends BaseRepository<PropertyConfig, UUID> {
  @Query(
      value =
          "select pc.description from PropertyConfig pc where pc.property = :property group by pc.description")
  List<String> findByPropertyDistinct(String property);

  List<PropertyConfig> findByPropertyAndDescription(String property, String description);
}
