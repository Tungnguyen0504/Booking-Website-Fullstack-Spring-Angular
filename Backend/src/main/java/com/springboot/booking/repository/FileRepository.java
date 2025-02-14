package com.springboot.booking.repository;

import com.springboot.booking.entities.File;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface FileRepository extends BaseRepository<File, UUID> {
  List<File> findByEntityIdAndEntityNameAndFileType(String entityId, String entityName, String fileType);
}
