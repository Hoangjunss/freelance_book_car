package com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTourScheduleResponse {
    private Integer id;
    private LocalDateTime timeStartTour;
    private  LocalDateTime timeEndTour;
    private Integer idTour;
    private Integer quantity;
    private  Double priceTour;
}
