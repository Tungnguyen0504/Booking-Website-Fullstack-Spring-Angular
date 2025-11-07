package vn.spring.booking.repository;

import vn.spring.booking.constant.enums.UserRole;
import vn.spring.booking.entities.Role;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface RoleRepository extends BaseRepository<Role, UUID> {
  Optional<Role> findByCode(UserRole code);
}
