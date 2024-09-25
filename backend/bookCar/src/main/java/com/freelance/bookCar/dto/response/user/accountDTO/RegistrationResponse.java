package com.freelance.bookCar.dto.response.user.accountDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponse {
    private Integer idAccount;
    private Integer idUser;
    private String name;
    private String email;
}
