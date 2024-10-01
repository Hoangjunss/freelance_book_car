package com.freelance.bookCar.controller.statistic;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.response.statistic.statisticMonthYear.StatisticMonthYear;
import com.freelance.bookCar.dto.response.statistic.statisticYear.StatisticYear;
import com.freelance.bookCar.services.statistic.statisticYear.StatisticYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/statistic")
@CrossOrigin(origins = "*")
public class StatisticController {
    @Autowired
    private StatisticYearService statisticYearService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/year")
    public ResponseEntity<ApiResponse<StatisticYear>> getStatisticYear(@RequestParam Integer year) {
       StatisticYear statisticYear=statisticYearService.getYear(year);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthyear")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYear(@RequestParam Integer month, @RequestParam Integer year) {
       StatisticMonthYear statisticYear=statisticYearService.getMonth(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/month/hotel")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYearHotel(@RequestParam Integer month, @RequestParam Integer year) {
        StatisticMonthYear statisticYear=statisticYearService.getMonthHotel(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/month/tour")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYearTour(@RequestParam Integer month, @RequestParam Integer year) {
        StatisticMonthYear statisticYear=statisticYearService.getMonthTour(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/month/tourism")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticMonthYearTourism(@RequestParam Integer month, @RequestParam Integer year) {
        StatisticMonthYear statisticYear=statisticYearService.getMonthTourism(year,month);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/today/hotel")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticTodayYearHotel() {
        StatisticMonthYear statisticYear=statisticYearService.getTodayHotel();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/today/tour")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticTodayYearTour() {
        StatisticMonthYear statisticYear=statisticYearService.getTodayTour();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/today/tourism")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticTodayYearTourism() {
        StatisticMonthYear statisticYear=statisticYearService.getTodayTourism();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/yesterday/hotel")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticYesterdayYearHotel() {
        StatisticMonthYear statisticYear=statisticYearService.getYesterdayHotel();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/yesterday/tour")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticYesterdayYearTour() {
        StatisticMonthYear statisticYear=statisticYearService.getYesterdayTour();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/yesterday/tourism")
    public ResponseEntity<ApiResponse<StatisticMonthYear>> getStatisticYesterdayYearTourism() {
        StatisticMonthYear statisticYear=statisticYearService.getYesterdayTourism();
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }

}
