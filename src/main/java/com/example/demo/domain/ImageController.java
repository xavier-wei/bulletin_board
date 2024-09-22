package com.example.demo.domain;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.nio.file.Paths;
@Controller
public class ImageController {


    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Path imagePath = Paths.get("src/main/resources/static/images").resolve(filename);
        Resource resource = new FileSystemResource(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 根據圖片類型設置
                .body(resource);
    }
}
