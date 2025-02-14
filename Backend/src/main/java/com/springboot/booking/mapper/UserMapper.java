package com.springboot.booking.mapper;

import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.library.common.mapper.BaseMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
    UserResponse toUserResponse(User user);
}
