package com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule;

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
public class UpdateTourScheduleRequest {
    private Integer id;
    private LocalDateTime timeStartTour;
    private Integer idTour;
    private Integer quantity;
    private  Double priceTour;
    private Integer idTourScheduleStatus;
}
