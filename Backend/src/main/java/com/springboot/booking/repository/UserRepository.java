package com.springboot.booking.repository;

import com.springboot.booking.entities.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
  @Query(value = "select u from User u where u.email = :email and u.status = 'ACTIVE'")
  Optional<User> findByEmail(String email);

  @Query(value = "select u from User u where u.phoneNumber = :phoneNumber and u.status = 'ACTIVE'")
  Optional<User> findByPhoneNumber(String phoneNumber);
}
