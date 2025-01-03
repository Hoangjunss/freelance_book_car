package com.freelance.bookCar.dto.response.user.userInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserInfoResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
