package com.freelance.bookCar.services.statistic.statisticYear;

import com.freelance.bookCar.dto.response.statistic.statisticMonthYear.StatisticMonthYear;
import com.freelance.bookCar.dto.response.statistic.statisticYear.StatisticYear;

public interface StatisticYearService {
    StatisticYear getYear(int year);
    StatisticMonthYear getMonth(int year,int month);
}
