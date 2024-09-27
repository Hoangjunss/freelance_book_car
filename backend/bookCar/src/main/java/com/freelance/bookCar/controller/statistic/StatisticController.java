package com.freelance.bookCar.controller.statistic;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.response.statistic.statisticMonthYear.StatisticMonthYear;
import com.freelance.bookCar.dto.response.statistic.statisticYear.StatisticYear;
import com.freelance.bookCar.services.statistic.statisticYear.StatisticYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic")
@CrossOrigin(origins = "*")
public class StatisticController {
    @Autowired
    private StatisticYearService statisticYearService;
    @GetMapping("/year")
    public ResponseEntity<ApiResponse<StatisticYear>> getStatisticYear(@RequestParam Integer year) {
       StatisticYear statisticYear=statisticYearService.getYear(year);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @GetMapping("/monthyear")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYear(@RequestParam Integer month, @RequestParam Integer year) {
       StatisticMonthYear statisticYear=statisticYearService.getMonth(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @GetMapping("/hotel")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYearHotel(@RequestParam Integer month, @RequestParam Integer year) {
        StatisticMonthYear statisticYear=statisticYearService.getMonthHotel(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @GetMapping("/tour")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYearTour(@RequestParam Integer month, @RequestParam Integer year) {
        StatisticMonthYear statisticYear=statisticYearService.getMonthTour(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @GetMapping("/tourism")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYearTourism(@RequestParam Integer month, @RequestParam Integer year) {
        StatisticMonthYear statisticYear=statisticYearService.getMonthTourism(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }

}
