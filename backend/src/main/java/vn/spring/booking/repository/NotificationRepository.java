package vn.spring.booking.repository;

import vn.spring.booking.entities.Notification;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification, UUID> {}
