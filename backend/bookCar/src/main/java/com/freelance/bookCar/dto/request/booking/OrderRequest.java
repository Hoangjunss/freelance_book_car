package com.freelance.bookCar.dto.request.booking;

import com.freelance.bookCar.dto.request.user.userInfoDTO.CreateUserInfoRequest;
import com.freelance.bookCar.dto.request.user.userJoinDTO.CreateUserJoinRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer id;
    private LocalDateTime dateBook;
    private double totalPrice;
    private Integer idUser;
    private List<CreateUserInfoRequest> createUserInfoRequest;
    private List<CreateUserJoinRequest> createUserJoinRequest;
    private Integer paymentMethod;

}
