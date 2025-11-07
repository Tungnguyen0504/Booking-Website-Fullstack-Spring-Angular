package vn.spring.booking.dto.response;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistrictResponse {
    private UUID districtId;
    private String districtName;
}
