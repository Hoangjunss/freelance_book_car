package com.freelance.bookCar.models.product.tour;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tour {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String startLocation;
    private String endLocation;
}
