package com.freelance.bookCar.dto.request.product.ticketDTO.tourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourismRequest {
    private String name;
    private String location;
    private String description;
    private double rating;
    private MultipartFile image;
}