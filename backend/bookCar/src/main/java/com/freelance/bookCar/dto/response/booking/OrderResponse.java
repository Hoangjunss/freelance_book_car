package com.freelance.bookCar.dto.response.booking;

import com.freelance.bookCar.dto.request.user.userInfoDTO.CreateUserInfoRequest;
import com.freelance.bookCar.dto.request.user.userJoinDTO.CreateUserJoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private LocalDateTime dateBook;
    private double totalPrice;
    private Integer idUser;
    private Integer paymentMethod;
}
