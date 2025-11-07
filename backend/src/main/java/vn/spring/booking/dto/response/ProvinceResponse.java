package vn.spring.booking.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProvinceResponse {
    private UUID provinceId;
    private String provinceName;
}
