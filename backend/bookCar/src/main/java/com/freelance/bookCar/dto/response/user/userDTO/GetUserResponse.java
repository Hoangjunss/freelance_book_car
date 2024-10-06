package com.freelance.bookCar.dto.response.user.userDTO;

import com.freelance.bookCar.models.user.TypeUser;
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
    private TypeUser role;
}
