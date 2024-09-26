package com.freelance.bookCar.dto.response.product.hotelDTO.hotel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateHotelResponse {

    private Integer id;

    private String name;
    private String contactInfo;
    private double pricePerNight;
    private String location;
    private boolean isActive;
    private double rating;
    private String image;



    // Getters and Setters
}