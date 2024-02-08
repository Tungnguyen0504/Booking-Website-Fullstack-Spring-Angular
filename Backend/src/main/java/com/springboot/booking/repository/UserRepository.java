package com.springboot.booking.repository;

import com.springboot.booking.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "select u from User u where u.email = :email and u.status = 'ACTIVE'")
  Optional<User> findByEmail(String email);

  @Query(value = "select u from User u where u.phoneNumber = :phoneNumber and u.status = 'ACTIVE'")
  Optional<User> findByPhoneNumber(String phoneNumber);
}
