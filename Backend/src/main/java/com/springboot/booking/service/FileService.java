package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
    public String encodeFileToString(String fileName) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(this.load(fileName).getContentAsByteArray());
    }

    public List<File> getFilesByEntityIdAndEntityName(String entityId, String entityName) {
        return fileRepository.findByEntityIdAndEntityName(entityId, entityName);
    }

    public File getFilesByEntityIdAndEntityNameDesc(String entityId, String entityName) {
        return fileRepository.findByEntityIdAndEntityNameDesc(entityId, entityName)
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND,
                        String.format("File %s", entityName)));
    }

    public File getFiles(String entityId, String entityName, String fileType) {
        return fileRepository.findByEntityIdAndEntityNameAndFileType(entityId, entityName, fileType)
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND,
                        String.format("File %s", entityName)));
    }

    @Transactional
    public void executeSaveFiles(List<MultipartFile> fileRequests, String prefixPath, String entityId, String entityName) {
        Set<String> checkExistedFilePaths = fileRequests
                .stream()
                .map(file -> prefixPath + "/" + file.getOriginalFilename())
                .collect(Collectors.toSet());

        List<File> files = fileRepository.findByEntityIdAndEntityName(entityId, entityName);
        ListIterator<File> iterator = files.listIterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (checkExistedFilePaths.contains(file.getFilePath())) {
                checkExistedFilePaths.remove(file.getFilePath());
            } else {
                fileRepository.delete(file);
                iterator.remove();
            }
        }

        files.addAll(checkExistedFilePaths.stream()
                .map(filePath -> File.builder()
                        .entityId(entityId)
                        .entityName(entityName)
                        .filePath(filePath)
                        .build())
                .toList());
        fileRepository.saveAll(files);

        saveMultiple(fileRequests, prefixPath);
    }
}
