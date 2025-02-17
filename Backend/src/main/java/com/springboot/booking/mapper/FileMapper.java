package com.springboot.booking.mapper;

import com.springboot.booking.dto.FileDto;
import com.springboot.booking.dto.response.AddressResponse;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.entities.File;
import com.springboot.booking.entities.User;
import com.springboot.booking.repository.FileRepository;
import com.springboot.booking.utils.ObjectUtils;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import vn.library.common.mapper.BaseMapper;
import vn.library.common.utils.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FileMapper implements BaseMapper<FileDto, File> {
  @Autowired protected FileRepository fileRepository;

//  abstract <T> List<FileResponse> mapFileResponse(UUID entityId, Class<T> entityClazz, String type);

  @SneakyThrows
  public <T> List<FileResponse> mapFileResponse(UUID entityId, Class<T> entityClazz, String type) {
    List<File> files =
        fileRepository.findByEntityIdAndEntityNameAndFileType(
            entityId, ObjectUtils.extractTableName(entityClazz), type);
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
