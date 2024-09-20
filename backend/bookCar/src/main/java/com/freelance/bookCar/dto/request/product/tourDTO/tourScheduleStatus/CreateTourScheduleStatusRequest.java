package com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourScheduleStatusRequest {
    private String name;
}
