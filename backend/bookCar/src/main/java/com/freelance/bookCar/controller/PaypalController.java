package com.freelance.bookCar.controller;

import com.freelance.bookCar.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/paypal")
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "/pay/success";
    public static final String CANCEL_URL = "/pay/cancel";

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payment(@ModelAttribute("order") Order order) {
        System.out.println("Received Order: " + order.toString());

        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:4200/payment-cancel" + CANCEL_URL,
                    "http://localhost:4200/payment-success" + "?vnp_ResponseCode=00&orderId=" + order.getId());
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(link.getHref() + "&orderId=" + order.getId());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating payment: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body("Error creating payment");
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             @RequestParam("orderId") Integer orderId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId, orderId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "redirect:http://localhost:4200/payment-success?vnp_ResponseCode=00&paymentId"+paymentId+"&payerId"+payerId+"&orderId"+orderId;
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
