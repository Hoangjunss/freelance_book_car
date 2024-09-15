package com.freelance.bookCar.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourResponse {
    private Integer id;
    private String name;
    private String description;
    private String startLocation;
    private String endLocation;
}
