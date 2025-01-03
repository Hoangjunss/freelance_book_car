package com.freelance.bookCar.dto.response.statistic.statisticYear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticYear {
   private List<StatisticMonth> statisticMonths;
}
