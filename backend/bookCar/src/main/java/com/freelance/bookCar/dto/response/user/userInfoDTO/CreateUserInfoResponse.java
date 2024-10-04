package com.freelance.bookCar.dto.response.user.userInfoDTO;

import com.freelance.bookCar.models.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserInfoResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Booking booking;
}
