package com.freelance.bookCar.controller;

import com.freelance.bookCar.services.VNPAYService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class VNPAYController {
    @Autowired
    private VNPAYService vnpayService;
    @GetMapping("/pay")
    public ResponseEntity<String> getPay(@RequestParam Long price, @RequestParam Integer id)throws UnsupportedEncodingException {
        String url= vnpayService.getPay(price,id);
       return  ResponseEntity.ok(url);
    }
    @GetMapping("/returnPay")
    public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> queryParams) throws IOException {
        String response=vnpayService.returnPay(queryParams.get("vnp_ResponseCode"),queryParams.get("contractId"));
        return  ResponseEntity.ok(response);
        }

    }
