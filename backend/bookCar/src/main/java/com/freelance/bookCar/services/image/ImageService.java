package com.freelance.bookCar.services.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {
    String saveImage(MultipartFile file);
}
