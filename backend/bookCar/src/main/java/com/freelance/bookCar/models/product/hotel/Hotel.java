package com.freelance.bookCar.models.product.hotel;

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
@Entity
public class Hotel {
    @Id
    private Integer id;
    private String name;
    private String contactInfo;
    private double pricePerNight;
    private String location;
    private boolean isActive;
    private double rating;
    // Getters and Setters
}