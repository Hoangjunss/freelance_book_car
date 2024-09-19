package com.freelance.bookCar.dto.request.product.tourDTO.tour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class FilterTourRequest {
    private LocalDateTime startTour;
    private LocalDateTime endTour;
    private String search;
    private Integer idTourStatus;
}
