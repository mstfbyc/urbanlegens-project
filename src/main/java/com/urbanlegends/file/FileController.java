package com.urbanlegends.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/api/1.0/hoax-attachments")
    ResponseEntity<FileAttachment> saveHoaxAttachment(MultipartFile file){
        return ResponseEntity.ok(fileService.saveHoaxAttachment(file));
    }
}
