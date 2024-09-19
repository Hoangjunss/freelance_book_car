package com.freelance.bookCar.dto.response.product.tourDTO.tourStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourStatusResponse {
    private Integer id;
    private String name;
}
