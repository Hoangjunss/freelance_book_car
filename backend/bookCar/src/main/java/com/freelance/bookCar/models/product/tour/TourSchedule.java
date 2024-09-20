package com.freelance.bookCar.models.product.tour;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TourSchedule {
    @Id
    private Integer id;
    private LocalDateTime timeStartTour;
    private LocalDateTime timeEndTour;
    private Integer idTour;
    private Integer quantity;
    private Double priceTour;
    private Integer idTourScheduleStatus;
}
