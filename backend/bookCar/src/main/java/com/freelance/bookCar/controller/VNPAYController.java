package com.freelance.bookCar.controller;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.services.VNPAYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
@RequestMapping("api/v1/vnpay")
@Controller
public class VNPAYController {
    @Autowired
    private VNPAYService vnpayService;
    @GetMapping("/pay")
    public ResponseEntity<String> getPay(@RequestParam("price") Long price, @RequestParam("id") Integer id)throws UnsupportedEncodingException {
        String url= vnpayService.getPay(price,id);
       return  ResponseEntity.ok(url);
    }
    @GetMapping("/returnPay")
    public ResponseEntity<ApiResponse<String>> paymentCallback(@RequestParam Map<String, String> queryParams) throws IOException {

        String response=vnpayService.returnPay(queryParams.get("vnp_ResponseCode"),queryParams.get("contractId"));
        ApiResponse<String> apiResponse;
        if ("success".equals(response)) {
            apiResponse = new ApiResponse<>(true, "Thanh toán thành công.", "success");
        } else {
            apiResponse = new ApiResponse<>(false, "Thanh toán thất bại. Vui lòng thử lại.", "failure");
        }

        return ResponseEntity.ok(apiResponse);
    }

    }
