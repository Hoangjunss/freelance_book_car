package com.freelance.bookCar.services.image;

import com.freelance.bookCar.services.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
@Slf4j
public class ImageServiceImpls implements ImageService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Override
    public String saveImage(MultipartFile imageFile) {
        log.info("Uploading image");
        Map<String, Object> resultMap = cloudinaryService.upload(imageFile);
        String imageUrl = (String) resultMap.get("url");

        return imageUrl;
    }


}
