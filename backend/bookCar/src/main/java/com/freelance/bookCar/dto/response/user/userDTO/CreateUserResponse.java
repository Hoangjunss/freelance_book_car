package com.freelance.bookCar.dto.response.user.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String type;
    private Integer idAccount;
}
