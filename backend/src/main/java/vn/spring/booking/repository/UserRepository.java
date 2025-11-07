package vn.spring.booking.repository;

import vn.spring.booking.entities.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
  @Query(value = "select u from User u where u.email = :email and u.status = 'ACTIVE'")
  Optional<User> findByEmail(String email);

  @Query(value = "select u from User u where u.phoneNumber = :phoneNumber and u.status = 'ACTIVE'")
  Optional<User> findByPhoneNumber(String phoneNumber);
}
