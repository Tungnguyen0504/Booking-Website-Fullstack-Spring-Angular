package vn.spring.booking.mapper;

import vn.spring.booking.dto.FileDto;
import vn.spring.booking.dto.response.FileResponse;
import vn.spring.booking.entities.File;
import vn.spring.booking.repository.FileRepository;
import vn.spring.booking.utils.ObjectUtils;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import vn.spring.common.mapper.BaseMapper;
import vn.spring.common.utils.FileUtil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FileMapper implements BaseMapper<FileDto, File> {
  @Autowired protected FileRepository fileRepository;

  @SneakyThrows
  public <T> List<FileResponse> toFileResponse(
      UUID entityId, Class<T> entityClazz, String fileType) {
    List<File> files =
        fileRepository.findByEntityIdAndEntityNameAndFileType(
            entityId, ObjectUtils.extractTableName(entityClazz), fileType);
    List<FileResponse> fileResponses = new ArrayList<>();
    for (File file : files) {
      Resource resource = FileUtil.getResource(file.getFilePath());
      FileResponse fileResponse =
          FileResponse.builder()
              .fileName(file.getFileName())
              .fileType(file.getFileType())
              .base64String(
                  "data:image/jpeg;base64,"
                      + Base64.getEncoder().encodeToString(resource.getContentAsByteArray()))
              .build();
      fileResponses.add(fileResponse);
    }
    return fileResponses;
  }
}
