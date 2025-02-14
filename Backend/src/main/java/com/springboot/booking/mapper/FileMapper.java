package com.springboot.booking.mapper;

import com.springboot.booking.dto.FileDto;
import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.entities.File;
import com.springboot.booking.entities.User;
import com.springboot.booking.repository.FileRepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import vn.library.common.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FileMapper implements BaseMapper<FileDto, File> {
  protected final FileRepository fileRepository;

  @Autowired
  protected FileMapper(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  abstract FileResponse toResponse(File file);
}
