package com.freelance.bookCar.dto.response.statistic.statisticYear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticMonth {
    private int month;
    private double priceHotel;
    private double priceTourism;
    private double priceTour;
}
