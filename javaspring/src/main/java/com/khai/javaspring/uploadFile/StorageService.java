package com.khai.javaspring.uploadFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@Slf4j
public class StorageService {

    @Value("${folder.path}")
    private String folderPath;

    public StorageService() {
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(folderPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFolderPath() {
        return folderPath;
    }

    /**
     * Phương thức lấy dữ liệu file từ server
     * @param fileName
     * @return
     */
    public byte[] getFileFromSystem(String fileName) {

        try {
            byte[] file = Files.readAllBytes(Path.of(folderPath + fileName));
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Phương thức xóa file
     * @param fileName
     * @return
     */
    public boolean deleteFileFromFileSystem(String fileName) {
        boolean isDeleted = false;
        try {
            Files.delete(Path.of(folderPath + fileName));
            isDeleted = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return isDeleted;
        }
    }

    /**
     * Phương thức upload file từ form người dùng
     * @param file
     * @param filePath
     * @return
     */
    public boolean uploadFileToSystem(MultipartFile file, String filePath) {

        boolean isSaveToFileSystem = false;

        try {
            // save file to file system
            file.transferTo(new java.io.File(filePath));
            isSaveToFileSystem = true;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            return isSaveToFileSystem;
        }
    }

    /**
     * Phương thức tải file từ ổ đĩa của máy server và chuyển thành kiểu Resource
     * @param filePath
     * @return
     */
    public Resource loadFileAsResource(String filePath) {
        try {
            URI uri = Paths.get(filePath).toUri();
            Resource resource = new UrlResource(uri);
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new RuntimeException("File not found " + filePath);
        } catch (MalformedURLException e) { // lỗi path không đung định dạng
            throw new RuntimeException("File not found " + filePath, e);
        }
    }

    /**
     * Phương thức lấy đường dẫn của tất cả file trong thư mục chỉ định
     * @return
     */
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Path.of(this.folderPath), 1)
                    .filter(path -> !path.equals(Path.of(this.folderPath)))
                    .map(Path.of(this.folderPath)::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Path.of(this.folderPath).toFile());
    }

}
