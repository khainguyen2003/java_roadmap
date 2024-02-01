package com.khai.javaspring.uploadFile;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Phương thưc xử lý yêu câu tải file phía client
     * @param fileName
     * @param request
     * @return tải về file được yêu cầu
     */
    @GetMapping("/file/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        String filePath = storageService.getFolderPath() + File.separator + fileName;
        Resource resource = storageService.loadFileAsResource(filePath);
        String contentType = "application/octet-stream";
        try {
            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.uploadFileToSystem(file, storageService.getFolderPath());
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
}
