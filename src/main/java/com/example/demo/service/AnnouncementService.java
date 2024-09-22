package com.example.demo.service;


import com.example.demo.model.Announcement;
import com.example.demo.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AnnouncementService {

    private static final String UPLOAD_DIRECTORY = "/uploads/images/";

    @Autowired
    private AnnouncementRepository announcementRepository;


    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    public Announcement findById(Long id) {
        return announcementRepository.findById(id).orElse(null);
    }

    public Announcement save(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public void delete(Long id) {
        announcementRepository.deleteById(id);
    }

    // 新增圖片保存邏輯
    public String saveImage(MultipartFile file) throws IOException {
        // 生成唯一的檔案名稱
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 將路徑設置為 src/main/resources/static/uploads/images
        Path uploadPath = Paths.get("src/main/resources/static/images");

        // 確保目錄存在
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 保存檔案
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        // 返回圖片的 URL
        return "/images/" + fileName; // 這裡的路徑保持不變
    }
}
