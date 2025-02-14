package com.springboot.booking.repository;

import com.springboot.booking.entities.Notification;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification, UUID> {}
