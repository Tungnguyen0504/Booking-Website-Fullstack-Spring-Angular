package vn.spring.booking.mapper;

import vn.spring.booking.dto.AccommodationDto;
import vn.spring.booking.dto.response.FileResponse;
import vn.spring.booking.entities.Accommodation;
import vn.spring.booking.entities.File;
import vn.spring.booking.repository.FileRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import vn.spring.common.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AccommodationMapper implements BaseMapper<AccommodationDto, Accommodation> {
  @Autowired
  protected FileRepository fileRepository;

  @Mapping(target = "base64String", expression = "java(fileRepository.findByEntityIdAndEntityNameAndFileType(null, null, null).get(0).getEntityName())")
  abstract FileResponse toResponse(File file);
}
