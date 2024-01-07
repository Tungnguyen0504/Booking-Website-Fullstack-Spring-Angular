package com.springboot.booking.repository;

import com.springboot.booking.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByFilePath(String filePath);

    List<File> findByEntityIdAndEntityName(String entityId, String entityName);
}
