package com.freelance.bookCar.services.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ImageServiceImpls implements ImageService {
    private static String UPLOAD_DIR = "src/main/resources/static/uploads/";


    @Override
    public String saveImage(MultipartFile file) {
        // Kiểm tra xem file có rỗng không
        if (file.isEmpty()) {

        }

        try {
            // Lấy tên file gốc
            String fileName = file.getOriginalFilename();

            // Đường dẫn tới nơi lưu file
            Path path = Paths.get(UPLOAD_DIR + fileName);

            // Lưu file vào đường dẫn
            Files.write(path, file.getBytes());
             return UPLOAD_DIR+fileName;
            // Trả về thông báo thành công

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
