package com.freelance.bookCar.dto.response.user.userDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse {
    private Integer id;
    private String name;
}
