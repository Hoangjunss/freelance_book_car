package com.freelance.bookCar.dto.request.product.tourDTO.tourStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTourStatusRequest {
    private Integer id;
    private String name;
}
