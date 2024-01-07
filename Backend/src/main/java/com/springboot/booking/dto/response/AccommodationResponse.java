package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class AccommodationResponse {
    private Long accommodationId;
    private String accommodationName;
    private AccommodationTypeResponse accommodationType;
    private String phone;
    private String email;
    private int star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
    private String fullAddress;
    private List<String> specialArounds;
    private List<String> filePaths;

    public static void main(String[] args) {
        String a = "Nhà thờ Thánh Joseph 0,1 km| Tràng Tiền Plaza 0,7 km| Sân bay Quốc tế Nội Bài 21,2 km|National Library of Vietnam 0,3 km| Ga Hà Nội 1 km| Sân bay Quốc tế Cát Bi 93,7 km|Hồ Hoàn Kiếm 0,4 km| Thành cổ 1 km|Hanoi City Court 0,4 km| Chợ Đồng Xuân 1 km|Phố Sách Hà Nội 0,4 km| Nhà hát Lớn Hà Nội 1,1 km|Đền Ngọc Sơn 0,4 km| Bảo tàng Mỹ thuật Việt Nam 1,2 km|Quảng Trường Đông Kinh Nghĩa Thục 0,4 km| Văn Miếu - Quốc Tử Giám 1,3 km|Nhà tù Lịch sử Hỏa Lò 0,5 km| Đền Quán Thánh 2 km|Nhà hát múa rối Thăng Long 0,6 km| Hồ Tây 2,7 km|Hanoi Post Office 0,6 km| Trung tâm thương mại Vincom Nguyễn Chí Thanh 4,2 km";
        for (String s : a.split("\\|")) {
            System.out.println(s);
        }
    }
}
