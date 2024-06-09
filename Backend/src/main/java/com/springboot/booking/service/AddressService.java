package com.springboot.booking.service;

import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.dto.response.DistrictResponse;
import com.springboot.booking.dto.response.ProvinceResponse;
import com.springboot.booking.dto.response.WardResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.Address;
import com.springboot.booking.model.entity.Province;
import com.springboot.booking.model.entity.Ward;
import com.springboot.booking.repository.AddressRepository;
import com.springboot.booking.repository.DistrictRepository;
import com.springboot.booking.repository.ProvinceRepository;
import com.springboot.booking.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;

    public List<Province> getTopProvince(int range) {
        return provinceRepository.findTopProvinceOrderById(range);
    }

    public List<ProvinceResponse> getAllProvince() {
        return provinceRepository.findAll()
                .stream()
                .map(p -> ProvinceResponse.builder()
                        .provinceId(p.getId())
                        .provinceName(p.getProvinceName())
                        .build()).toList();
    }

    public List<DistrictResponse> getDistrictsByProvince(Long provinceId) {
        return districtRepository.findByProvinceId(provinceId)
                .stream()
                .map(d -> DistrictResponse.builder()
                        .districtId(d.getId())
                        .districtName(d.getDistrictName())
                        .build()).toList();
    }

    public List<WardResponse> getWardsByDistrict(Long districtId) {
        return wardRepository.findByDistrictId(districtId)
                .stream()
                .map(w -> WardResponse.builder()
                        .wardId(w.getId())
                        .wardName(w.getWardName())
                        .build()).toList();
    }

    public String getFullAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        if(address == null) {
            return "";
        }
        StringBuilder fullAddressBuilder = new StringBuilder();
        fullAddressBuilder.append(address.getSpecificAddress());
        fullAddressBuilder.append(", ");
        fullAddressBuilder.append(address.getWard().getWardName());
        fullAddressBuilder.append(", ");
        fullAddressBuilder.append(address.getWard().getDistrict().getDistrictName());
        fullAddressBuilder.append(", ");
        fullAddressBuilder.append(address.getWard().getDistrict().getProvince().getProvinceName());

        return fullAddressBuilder.toString();
    }

    public Address createAddress(Long wardId, String specificAddress) {
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND));
        return addressRepository.save(Address.builder()
                .specificAddress(specificAddress)
                .ward(ward)
                .build());
    }

    public void update(Long id, Long wardId, String specificAddress) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "địa chỉ"));
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "phường/xã"));
        address.setWard(ward);
        address.setSpecificAddress(specificAddress);
        addressRepository.save(address);
    }
}
