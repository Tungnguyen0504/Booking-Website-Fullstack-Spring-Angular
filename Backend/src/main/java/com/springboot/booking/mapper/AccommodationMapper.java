package com.springboot.booking.mapper;

import com.springboot.booking.dto.AccommodationDto;
import com.springboot.booking.dto.FileDto;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.entities.Accommodation;
import com.springboot.booking.entities.File;
import com.springboot.booking.repository.FileRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import vn.library.common.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AccommodationMapper implements BaseMapper<AccommodationDto, Accommodation> {
  @Autowired
  protected FileRepository fileRepository;

  @Mapping(target = "base64String", expression = "java(fileRepository.findByEntityIdAndEntityNameAndFileType(null, null, null).get(0).getEntityName())")
  abstract FileResponse toResponse(File file);
}
