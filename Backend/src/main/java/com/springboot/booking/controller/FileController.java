package com.springboot.booking.controller;

import com.springboot.booking.dto.request.LoadMultipleFileRequest;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.springboot.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + "/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/load-multiple")
    public ResponseEntity<List<FileResponse>> getListFiles(@RequestBody LoadMultipleFileRequest request) {
        List<FileResponse> files = fileService.loadMultiple(request.getFiles()).stream()
                .map(file -> {
                    try {
                        return FileResponse.builder()
                                .name(file.getFilename())
                                .type(MediaType.IMAGE_JPEG_VALUE)
                                .fileByte(file.getContentAsByteArray())
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return ResponseEntity.ok(files);
    }

    @GetMapping("/load")
    public ResponseEntity<FileResponse> getFile(@RequestParam String filename) throws IOException {
        Resource file = fileService.load(filename);
        return ResponseEntity.ok(FileResponse.builder()
                .name(file.getFilename())
                .type(MediaType.IMAGE_JPEG_VALUE)
                .fileByte(file.getContentAsByteArray())
                .build());
    }
}
