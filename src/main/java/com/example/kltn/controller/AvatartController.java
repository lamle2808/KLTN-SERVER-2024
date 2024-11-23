package com.example.kltn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.kltn.entity.Avatar;
import com.example.kltn.service.AvatarService;
import com.example.kltn.service.CloudinaryService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/avatars")
public class AvatartController {
    private final AvatarService avatarService;
    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/saveOrUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveOrUpdate(@RequestParam("file") MultipartFile file) {
        try {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            if (bi == null) {
                return ResponseEntity.badRequest().body("Error !!");
            }
            Map result = cloudinaryService.upload(file);
            Avatar avatar = new Avatar();
            avatar.setImageLink((String) result.get("url"));
            avatar.setName((String) result.get("original_filename"));
            avatar.setIdCloud((String) result.get("public_id"));
            // Sử dụng DateTimeFormatter để chuyển đổi chuỗi thành đối tượng Instant
            DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
            Instant instant = Instant.from(formatter.parse((String) result.get("created_at")));
            Date date = Date.from(instant);
            avatar.setDate(date);
            avatar.setType((String) result.get("format"));
            int bytes = (int) result.get("bytes");
            double size = (double) bytes / 1024;
            String sizeFormat = String.format("%.3f", size);
            avatar.setSize(sizeFormat + "KB");
            return ResponseEntity.ok().body(avatarService.addAvatar(avatar));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("There is an exception when execute !! --> " + exception);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws IOException {
        try {
            if (!avatarService.check(id)) {
                return ResponseEntity.badRequest().body("ImageProduct not found !!");
            }
            Avatar avatar = avatarService.getById(id);
            if (!avatar.getIdCloud().equals("default")) {
                Map result = cloudinaryService.delete(avatar.getIdCloud());
                return ResponseEntity.ok().body(result);
            }
            return ResponseEntity.ok().body(avatarService.remove(id));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("There is an exception when execute !! --> " + exception);
        }

    }
}
