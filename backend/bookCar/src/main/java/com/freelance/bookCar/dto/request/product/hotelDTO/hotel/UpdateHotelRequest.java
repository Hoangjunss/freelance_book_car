package com.freelance.bookCar.dto.request.product.hotelDTO.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UpdateHotelRequest {
    private Integer id;

    private String name;
    private String contactInfo;
    private double pricePerNight;
    private String location;
    private String isActive;
    private double rating;
    private MultipartFile image;



    // Getters and Setters
}