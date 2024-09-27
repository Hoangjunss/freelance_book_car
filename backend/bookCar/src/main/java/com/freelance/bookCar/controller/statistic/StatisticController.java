package com.freelance.bookCar.controller.statistic;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.response.product.voucherDTO.voucher.GetVoucherResponse;
import com.freelance.bookCar.dto.response.statistic.StatisticYear;
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
    public ResponseEntity<ApiResponse<StatisticYear>> getStatisticYear(@RequestParam Integer id) {
       StatisticYear statisticYear=statisticYearService.getYear(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get Voucher successfully", statisticYear));
    }

}
