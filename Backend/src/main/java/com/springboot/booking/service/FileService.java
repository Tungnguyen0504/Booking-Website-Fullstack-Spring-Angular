package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.model.BException;
import com.springboot.booking.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final Path root = Paths.get(AbstractConstant.FILE_UPLOAD_ROOT);

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
            Path path = Paths.get(AbstractConstant.FILE_UPLOAD_ROOT, paths);
            if (!Files.exists(path))
                Files.createDirectories(path);

            StringBuilder fileNameBuilder = new StringBuilder();
            fileNameBuilder.append(Objects.requireNonNull(file.getOriginalFilename()));
            fileNameBuilder.append("_");
            fileNameBuilder.append(DatetimeUtil.LocaleTimeddMMyyyyHHmmss(LocalDateTime.now()));
            fileNameBuilder.append(getExtension(file.getOriginalFilename()).get());

            Path filePath = path.resolve(fileNameBuilder.toString());
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString();
        } catch (Exception e) {
            throw new BException("Error: " + e.getMessage());
        }
    }

    public void saveMultiple(List<MultipartFile> files, String... paths) {
        files.stream().forEach(file -> save(file, paths));
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
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
