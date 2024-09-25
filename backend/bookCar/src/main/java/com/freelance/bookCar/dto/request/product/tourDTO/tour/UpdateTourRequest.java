package com.freelance.bookCar.dto.request.product.tourDTO.tour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTourRequest {
    private Integer id;
    private String name;
    private String description;
    private String startLocation;
    private String endLocation;
    private Boolean isActive;
    private MultipartFile image;
}
