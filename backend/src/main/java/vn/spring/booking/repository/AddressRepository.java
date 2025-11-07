package vn.spring.booking.repository;

import vn.spring.booking.entities.Address;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface AddressRepository extends BaseRepository<Address, UUID> {}
