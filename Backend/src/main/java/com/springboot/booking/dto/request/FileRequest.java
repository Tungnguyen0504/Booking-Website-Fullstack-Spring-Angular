package com.springboot.booking.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {

    private String entityId;
    private String entityName;
    private String filePath;
}
