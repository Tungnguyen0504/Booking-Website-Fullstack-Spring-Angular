package com.springboot.booking.dto.request;

import lombok.Data;

@Data
public class FileRequest {

    private String entityId;
    private String entityName;
    private String filePath;
}
