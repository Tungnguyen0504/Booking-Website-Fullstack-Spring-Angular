package vn.spring.booking.repository;

import vn.spring.booking.entities.Category;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface PropertyConfigRepository extends BaseRepository<Category, UUID> {
  @Query(
      value =
          "select pc.description from Category pc where pc.property = :property group by pc.description")
  List<String> findByPropertyDistinct(String property);

  List<Category> findByPropertyAndDescription(String property, String description);
}
