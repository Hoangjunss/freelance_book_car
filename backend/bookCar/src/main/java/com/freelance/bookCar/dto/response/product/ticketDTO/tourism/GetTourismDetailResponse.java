package com.freelance.bookCar.dto.response.product.ticketDTO.tourism;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTourismDetailResponse {
    private Integer id;
    private String name;
    private String location;
    private String description;
    private double rating;
    private String image;
    private Double price;
}
