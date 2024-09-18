package com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourScheduleResponse {
    private Integer id;
    private LocalDateTime timeStartTour;
    private  LocalDateTime timeEndTour;
    private Integer idTour;
    private Integer quantity;
    private  Double priceTour;
}
