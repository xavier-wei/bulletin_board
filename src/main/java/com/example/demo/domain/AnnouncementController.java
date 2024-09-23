package com.example.demo.domain;


import com.example.demo.model.Announcement;
import com.example.demo.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("")
    public String listAnnouncements(Model model) {
        model.addAttribute("announcements", announcementService.findAll());
        return "list";  // 直接返回模板的名稱
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "form";  // 直接返回模板的名稱
    }

    @PostMapping
    public String createAnnouncement(@ModelAttribute Announcement announcement) {
        System.out.println("announcement>>" + announcement);
        announcementService.save(announcement); // 儲存公告到資料庫
        return "redirect:/announcements"; // 提交後重定向到公告列表頁面
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Announcement announcement = announcementService.findById(id);
        if (announcement == null) {
            return "redirect:/announcements"; // 如果找不到，重定向到列表
        }
        model.addAttribute("announcement", announcement);
        return "form";  // 直接返回模板的名稱
    }

    @PostMapping("/update")
    public String updateAnnouncement(@ModelAttribute Announcement announcement) {
        System.out.println("Updating announcement: " + announcement);
        announcementService.update(announcement); // 更新公告資料
        return "redirect:/announcements"; // 提交後重定向到公告列表頁面
    }

    @GetMapping("/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementService.delete(id);
        return "redirect:/announcements";
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // 使用 AnnouncementService 保存圖片並獲取圖片 URL
            String imageUrl = announcementService.saveImage(file);
            response.put("imageUrl", imageUrl);

            System.out.println("response>>>" + response);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "圖片上傳失敗");
        }
        return response;
    }
}
