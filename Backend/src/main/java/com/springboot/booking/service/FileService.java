package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final Path root = Paths.get(Constant.FILE_UPLOAD_ROOT);

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    private Optional<String> getExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(file -> file.contains("."))
                .map(file -> file.substring(file.lastIndexOf(".")));
    }

    public String save(MultipartFile file, String... paths) {
        try {
            Path path = Paths.get(Constant.FILE_UPLOAD_ROOT, paths);
            if (!Files.exists(path))
                Files.createDirectories(path);
            Path filePath = path.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMultiple(List<MultipartFile> files, String... paths) {
        files.forEach(file -> save(file, paths));
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Resource> loadMultiple(List<String> fileNames) {
        return fileNames.stream().map(this::load).collect(Collectors.toList());
    }

    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    public List<File> getFilesByEntityIdAndEntityName(String entityId, String entityName) {
        return fileRepository.findByEntityIdAndEntityName(entityId, entityName);
    }

    public String encodeFileToString(String fileName) throws IOException {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(this.load(fileName).getContentAsByteArray());
    }
}
