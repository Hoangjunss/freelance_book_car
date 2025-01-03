package com.freelance.bookCar.dto.request.user.userJoinDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserJoinRequest {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
