package com.springboot.booking.repository;

import com.springboot.booking.entities.Address;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface AddressRepository extends BaseRepository<Address, UUID> {}
