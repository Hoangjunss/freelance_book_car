package com.freelance.bookCar.dto.request.product.hotelDTO.hotel;

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

public class CreateHotelRequest {


    private String name;
    private String contactInfo;
    private double pricePerNight;
    private String location;
    private String status;
    private double rating;



    // Getters and Setters
}