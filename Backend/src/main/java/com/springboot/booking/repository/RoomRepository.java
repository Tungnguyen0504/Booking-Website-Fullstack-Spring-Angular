package com.springboot.booking.repository;

import com.springboot.booking.entities.Room;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface RoomRepository extends BaseRepository<Room, UUID> {
  @Query(
      value =
          "select r from Room r where r.accommodation.id = :accommodationId AND LOWER(r.roomType) = LOWER(:roomType) AND r.status = 'ACTIVE'")
  List<Room> findByAccommodationIdAndRoomType(UUID accommodationId, String roomType);

  @Query(
      value =
          "select r from Room r where r.accommodation.id = :accommodationId AND r.status = 'ACTIVE'")
  List<Room> findByAccommodationId(Long accommodationId);
}
