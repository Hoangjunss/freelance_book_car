package com.freelance.bookCar.models.product.ticket;

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
public class Tourism {
    @Id
    private Long id;

    private String name;
    private String location;
    private String description;
    private double rating;



    // Getters and Setters
}