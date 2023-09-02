package com.springboot.booking.repository;

import com.springboot.booking.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);
    @Query(value = "SELECT * FROM user WHERE email = :emailOrPhoneNumber OR phone_number = :emailOrPhoneNumber", nativeQuery = true)
    Optional<User> findByEmailOrPhoneNumber(String emailOrPhoneNumber);
}
