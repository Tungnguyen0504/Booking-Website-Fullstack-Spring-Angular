package com.springboot.booking.mapper;

import com.springboot.booking.constant.enums.UserRole;
import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.dto.response.AddressResponse;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.entities.Address;
import com.springboot.booking.entities.Role;
import com.springboot.booking.entities.User;
import com.springboot.booking.repository.RoleRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import vn.library.common.constants.enums.BaseResponseCode;
import vn.library.common.exception.BaseException;
import vn.library.common.mapper.BaseMapper;
import vn.library.common.utils.CryptoUtil;
import vn.library.common.utils.ObjectUtil;

import java.security.Principal;
import java.util.List;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {CryptoUtil.class})
public abstract class UserMapper implements BaseMapper<UserDto, User> {
  @Autowired protected RoleRepository roleRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", source = "principal.name")
  @Mapping(target = "bookings", ignore = true)
  @Mapping(target = "notifications", ignore = true)
  @Mapping(
      target = "password",
      expression = "java(CryptoUtil.encryptPassword(CryptoUtil.decrypt(request.getPassword())))")
  @Mapping(target = "roles", expression = "java(mapRole(request.getRole()))")
  public abstract User toEntity(RegisterRequest request, Principal principal);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", expression = "java(mapRole(userDto.getRole()))")
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdTime", ignore = true)
  @Mapping(target = "updatedBy", source = "principal.name")
  public abstract void toEntityModify(
      @MappingTarget User user, UserDto userDto, Principal principal);

  @Mapping(target = "address", qualifiedByName = "mapAddress")
  @Mapping(target = "file", qualifiedByName = "mapFile")
  public abstract UserResponse toResponse(User user);

  List<Role> mapRole(UserRole role) {
    return List.of(
        roleRepository
            .findByCode(role)
            .orElseThrow(
                () -> new BaseException(BaseResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND)));
  }

  @Named("mapAddress")
  AddressResponse mapAddress(Address address) {
    if (ObjectUtil.isEmpty(address)) {
      return null;
    }
    StringBuilder fullAddress = new StringBuilder();
    fullAddress
        .append(address.getSpecificAddress())
        .append(", ")
        .append(address.getWard().getWardName())
        .append(", ")
        .append(address.getWard().getDistrict().getDistrictName())
        .append(", ")
        .append(address.getWard().getDistrict().getProvince().getProvinceName());

    return AddressResponse.builder()
        .wardId(address.getWardId())
        .specificAddress(address.getSpecificAddress())
        .fullAddress(fullAddress.toString())
        .build();
  }

  @Named("mapFile")
  FileResponse mapFile(User user) {
    return Mappers.getMapper(FileMapper.class)
        .toFileResponse(user.getId(), User.class, MediaType.IMAGE_JPEG_VALUE)
        .stream()
        .findFirst()
        .orElse(null);
  }
}
