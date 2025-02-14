package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.entities.File;
import com.springboot.booking.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    @SneakyThrows(IOException.class)
    public String encodeImageFileToString(String filePath) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(this.load(filePath).getContentAsByteArray());
    }

    public List<File> getFilesByEntityIdAndEntityName(String entityId, String entityName) {
        return fileRepository.findByEntityIdAndEntityName(entityId, entityName);
    }

    public List<File> getFiles(String entityId, String entityName, String fileType) {
        return fileRepository.findByEntityIdAndEntityNameAndFileType(entityId, entityName, fileType);
    }

    @Transactional
    public void executeSaveImages(List<MultipartFile> fileRequests, String prefixPath, String entityId, String entityName) {
        Set<Map<String, String>> existedFilePaths = fileRequests
                .stream()
                .map(file -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("fileName", file.getOriginalFilename());
                    map.put("filePath", prefixPath + "/" + file.getOriginalFilename());
                    return map;
                })
                .collect(Collectors.toSet());

        List<File> files = fileRepository.findByEntityIdAndEntityName(entityId, entityName);
        ListIterator<File> iterator = files.listIterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            for (Map<String, String> map : existedFilePaths) {
                if (map.get("filePath").equals(file.getFilePath())) {
                    existedFilePaths.remove(map);
                } else {
                    fileRepository.delete(file);
                    iterator.remove();
                }
            }
        }

        files.addAll(existedFilePaths.stream()
                .map(filePath -> File.builder()
                        .entityId(entityId)
                        .entityName(entityName)
                        .fileType(MediaType.IMAGE_JPEG_VALUE)
                        .filePath(filePath.get("filePath"))
                        .build())
                .toList());
        fileRepository.saveAll(files);

        saveMultiple(fileRequests, prefixPath);
    }
}
