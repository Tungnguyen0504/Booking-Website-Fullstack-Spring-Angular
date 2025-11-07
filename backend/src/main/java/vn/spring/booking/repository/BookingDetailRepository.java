package vn.spring.booking.repository;

import vn.spring.booking.entities.BookingDetail;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface BookingDetailRepository extends BaseRepository<BookingDetail, UUID> {}
