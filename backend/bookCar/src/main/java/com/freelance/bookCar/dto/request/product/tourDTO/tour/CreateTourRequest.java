package com.freelance.bookCar.dto.request.product.tourDTO.tour;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourRequest {
    private String name;
    private String description;
    private String startLocation;
    private String endLocation;
    private Boolean isActive;
    private MultipartFile imageFirst;
    private MultipartFile imageSecond;
    private MultipartFile imageThird;
    private MultipartFile imageMap;
}
