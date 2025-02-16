package com.springboot.booking.mapper;

import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.response.AddressResponse;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.entities.File;
import com.springboot.booking.entities.User;
import com.springboot.booking.repository.FileRepository;
import com.springboot.booking.utils.FileUtil;
import com.springboot.booking.utils.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import vn.library.common.mapper.BaseMapper;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UserMapper implements BaseMapper<UserDto, User> {
  @Autowired
  protected FileRepository fileRepository;

  abstract UserResponse toResponse(User user);
}
