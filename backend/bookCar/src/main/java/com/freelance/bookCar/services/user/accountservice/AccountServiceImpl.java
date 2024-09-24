package com.freelance.bookCar.services.user.accountservice;

import com.freelance.bookCar.dto.request.user.accountDTO.RegistrationRequest;
import com.freelance.bookCar.dto.request.user.userDTO.CreateUserRequest;
import com.freelance.bookCar.dto.response.user.accountDTO.RegistrationResponse;
import com.freelance.bookCar.dto.response.user.userDTO.CreateUserResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.models.user.Account;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.AccountRepository;
import com.freelance.bookCar.services.user.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class AccountServiceImpl {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        if(usernameExists(registrationRequest.getName())){
            throw new CustomException(Error.USER_ALREADY_EXISTS);
        }


        CreateUserRequest createUserRequest=CreateUserRequest.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .build();
        CreateUserResponse createUserResponse=userService.createUser(createUserRequest);
        Account account=Account.builder()
                .id(getGenerationId())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .user(modelMapper.map(createUserResponse, User.class)).build();
        Account accountSave=accountRepository.save(account);
        RegistrationResponse registrationResponse=RegistrationResponse.builder()
                .idUser(createUserResponse.getId())
                .email(createUserResponse.getEmail())
                .name(createUserResponse.getName())
                .idAccount(accountSave.getId())
                .build();
        return registrationResponse;
    }
    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        // Use most significant bits and ensure it's within the integer range
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
