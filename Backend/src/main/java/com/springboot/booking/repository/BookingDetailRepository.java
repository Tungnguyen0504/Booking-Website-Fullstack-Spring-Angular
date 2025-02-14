package com.springboot.booking.repository;

import com.springboot.booking.entities.BookingDetail;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface BookingDetailRepository extends BaseRepository<BookingDetail, UUID> {}
