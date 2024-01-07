package com.springboot.booking.mapper;


import com.springboot.booking.dto.response.AccommodationResponse;
import com.springboot.booking.model.entity.Accommodation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoMapper {
    AutoMapper MAPPER = Mappers.getMapper(AutoMapper.class);

    @Mapping(source = "id", target = "accommodationId")
    @Mapping(target = "accommodationType", ignore = true)
    @Mapping(target = "specialArounds", ignore = true)
    AccommodationResponse mapToAccommodationResponse(Accommodation accommodation);
}
