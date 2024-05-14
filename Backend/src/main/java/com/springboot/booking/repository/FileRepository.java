package com.springboot.booking.repository;

import com.springboot.booking.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByFilePath(String filePath);

    List<File> findByEntityIdAndEntityName(String entityId, String entityName);

    @Query(value = "select f from File f " +
            "where f.entityId = :entityId and f.entityName = :entityName " +
            "order by STR_TO_DATE(f.modifiedAt, '%Y-%m-%d %H:%i:%s.%f') desc limit 1")
    Optional<File> findByEntityIdAndEntityNameDesc(String entityId, String entityName);

    @Query(value = "select f from File f " +
            "where f.entityId = :entityId and f.entityName = :entityName and f.fileType = :fileType")
    Optional<File> findByEntityIdAndEntityNameAndFileType(String entityId, String entityName, String fileType);
}
