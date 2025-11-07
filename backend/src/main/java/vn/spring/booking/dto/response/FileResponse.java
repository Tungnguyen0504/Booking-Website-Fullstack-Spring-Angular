package vn.spring.booking.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class FileResponse {
    private String fileName;
    private String fileType;
    private String base64String;
}
