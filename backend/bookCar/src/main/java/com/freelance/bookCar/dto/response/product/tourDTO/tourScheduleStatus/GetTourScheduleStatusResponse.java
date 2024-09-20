package com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTourScheduleStatusResponse {
    private Integer id;
    private String name;
}

