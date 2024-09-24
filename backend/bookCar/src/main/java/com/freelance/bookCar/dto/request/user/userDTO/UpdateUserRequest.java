package com.freelance.bookCar.dto.request.user.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private Integer id;
    private String name;
    private String phone;
    private String address;
}
