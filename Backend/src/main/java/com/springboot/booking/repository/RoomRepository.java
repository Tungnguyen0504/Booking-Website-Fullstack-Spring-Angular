package com.springboot.booking.repository;

import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(value = "select r from Room r where r.accommodation.id = :accommodationId AND LOWER(r.roomType) = LOWER(:roomType)")
    List<Room> findByAccommodationIdAndRoomType(Long accommodationId, String roomType);
}
