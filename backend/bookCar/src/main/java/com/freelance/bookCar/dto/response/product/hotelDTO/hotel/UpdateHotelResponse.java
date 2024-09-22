package com.freelance.bookCar.dto.response.product.hotelDTO.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UpdateHotelResponse {


    private String name;
    private String contactInfo;
    private double pricePerNight;
    private String location;
    private boolean isActive;
    private double rating;



    // Getters and Setters
}
