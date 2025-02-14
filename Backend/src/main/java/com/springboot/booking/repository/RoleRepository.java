package com.springboot.booking.repository;

import com.springboot.booking.entities.Role;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface RoleRepository extends BaseRepository<Role, UUID> {}
