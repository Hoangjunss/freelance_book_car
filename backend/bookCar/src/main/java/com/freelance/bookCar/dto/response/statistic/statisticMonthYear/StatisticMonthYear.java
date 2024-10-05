package com.freelance.bookCar.dto.response.statistic.statisticMonthYear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticMonthYear {
    private double totalPrice;
    private double avgPrice;
    private double totalTicket;
    private double avgTicket;
}
