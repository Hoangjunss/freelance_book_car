package com.freelance.bookCar.services.user.userservice;

import com.freelance.bookCar.dto.request.user.accountDTO.LoginRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.RefreshToken;
import com.freelance.bookCar.dto.request.user.accountDTO.RegistrationRequest;
import com.freelance.bookCar.dto.request.user.userDTO.CreateUserRequest;
import com.freelance.bookCar.dto.request.user.userDTO.UpdateUserRequest;
import com.freelance.bookCar.dto.response.user.accountDTO.LoginResponse;
import com.freelance.bookCar.dto.response.user.accountDTO.RegistrationResponse;
import com.freelance.bookCar.dto.response.user.userDTO.CreateUserResponse;
import com.freelance.bookCar.dto.response.user.userDTO.UpdateUserResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest userRequest);
    UpdateUserResponse updateUser(UpdateUserRequest userRequest);
    LoginResponse generateRefreshToken(RefreshToken token);
    LoginResponse login(LoginRequest loginRequest);
    RegistrationResponse registration(RegistrationRequest registrationRequest) ;
}
