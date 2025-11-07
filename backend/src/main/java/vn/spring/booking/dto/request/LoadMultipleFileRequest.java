package vn.spring.booking.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadMultipleFileRequest {
    private List<String> files;
}
