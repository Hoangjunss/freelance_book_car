package com.freelance.bookCar.controller.auth;

import com.freelance.bookCar.dto.ApiResponse;
import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.LoginRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.RefreshToken;
import com.freelance.bookCar.dto.request.user.accountDTO.RegistrationRequest;
import com.freelance.bookCar.dto.response.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.user.accountDTO.LoginResponse;
import com.freelance.bookCar.dto.response.user.accountDTO.RegistrationResponse;
import com.freelance.bookCar.dto.response.user.userDTO.GetUserResponse;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.services.user.userservice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService accountService;
            @PostMapping("/registration")
    public ResponseEntity<ApiResponse<RegistrationResponse>> registration(@ModelAttribute @Valid RegistrationRequest registrationRequest){
        RegistrationResponse registrationResponse=accountService.registration(registrationRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registration  successfully", registrationResponse));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@ModelAttribute @Valid LoginRequest loginRequest){
        LoginResponse loginResponse=accountService.login(loginRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login  successfully", loginResponse));
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@ModelAttribute @Valid RefreshToken refreshToken){
        LoginResponse loginResponse=accountService.generateRefreshToken(refreshToken);
        return ResponseEntity.ok(new ApiResponse<>(true, "RefreshToken  successfully", loginResponse));
    }
    @GetMapping("/current-user")
    public ResponseEntity<ApiResponse<GetUserResponse>>  getCurrentUser() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GetUserResponse getUserResponse=GetUserResponse.builder().id(userDetails.getId()).name(userDetails.getUsername()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, "RefreshToken  successfully", getUserResponse));
    }

}
