package vn.spring.booking.mapper;

import vn.spring.booking.constant.enums.UserRole;
import vn.spring.booking.dto.UserDto;
import vn.spring.booking.dto.request.RegisterRequest;
import vn.spring.booking.dto.response.AddressResponse;
import vn.spring.booking.dto.response.FileResponse;
import vn.spring.booking.dto.response.UserResponse;
import vn.spring.booking.entities.Address;
import vn.spring.booking.entities.Role;
import vn.spring.booking.entities.User;
import vn.spring.booking.repository.RoleRepository;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import vn.spring.common.constants.enums.BaseResponseCode;
import vn.spring.common.exception.BaseException;
import vn.spring.common.mapper.BaseMapper;
import vn.spring.common.utils.CryptoUtil;
import vn.spring.common.utils.ObjectUtil;

import java.security.Principal;
import java.util.List;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    imports = {CryptoUtil.class, UserRole.class})
public abstract class UserMapper implements BaseMapper<UserDto, User> {
  @Autowired protected RoleRepository roleRepository;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", source = "principal.name")
  @Mapping(
      target = "password",
      expression = "java(CryptoUtil.encryptPassword(CryptoUtil.decrypt(request.getPassword())))")
  @Mapping(target = "roles", expression = "java(mapRole())")
  public abstract User toEntity(RegisterRequest request, Principal principal);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "roles", expression = "java(mapRole(userDto.getRole(UserRole.USER)))")
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
