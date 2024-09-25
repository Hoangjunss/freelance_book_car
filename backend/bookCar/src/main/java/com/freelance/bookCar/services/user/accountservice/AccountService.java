package com.freelance.bookCar.services.user.accountservice;

import com.freelance.bookCar.dto.request.user.accountDTO.LoginRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.RefreshToken;
import com.freelance.bookCar.dto.request.user.accountDTO.RegistrationRequest;
import com.freelance.bookCar.dto.response.user.accountDTO.LoginResponse;
import com.freelance.bookCar.dto.response.user.accountDTO.RegistrationResponse;

public interface AccountService {
    LoginResponse generateRefreshToken(RefreshToken token);
    LoginResponse login(LoginRequest loginRequest);
    RegistrationResponse registration(RegistrationRequest registrationRequest);
}
