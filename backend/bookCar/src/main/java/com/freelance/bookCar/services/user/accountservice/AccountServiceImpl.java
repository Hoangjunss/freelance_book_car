package com.freelance.bookCar.services.user.accountservice;

import com.freelance.bookCar.dto.request.user.accountDTO.LoginRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.RefreshToken;
import com.freelance.bookCar.dto.request.user.accountDTO.RegistrationRequest;
import com.freelance.bookCar.dto.request.user.userDTO.CreateUserRequest;
import com.freelance.bookCar.dto.response.user.accountDTO.LoginResponse;
import com.freelance.bookCar.dto.response.user.accountDTO.RegistrationResponse;
import com.freelance.bookCar.dto.response.user.userDTO.CreateUserResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.user.Account;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.AccountRepository;
import com.freelance.bookCar.security.JwtTokenUtil;
import com.freelance.bookCar.security.OurUserDetailsService;
import com.freelance.bookCar.services.user.userservice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        if(usernameExists(registrationRequest.getName())){
            throw new CustomException(Error.USER_ALREADY_EXISTS);
        }

        if(registrationRequest.getEmail() == null){
            throw new CustomException(Error.USER_INVALID_EMAIL);
        }
        if(registrationRequest.getName() == null){
            throw new CustomException(Error.USER_INVALID_NAME);
        }
        if(registrationRequest.getPassword() == null){
            throw new CustomException(Error.USER_INVALID_PASSWORD);
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

        try {
            Account accountSave=accountRepository.save(account);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while saving accounting: {}", e.getMessage(), e);
            throw new CustomException(Error.ACCOUNT_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in saving accounting: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        RegistrationResponse registrationResponse=RegistrationResponse.builder()
                .idUser(createUserResponse.getId())
                .email(createUserResponse.getEmail())
                .name(createUserResponse.getName())
                .build();
        return registrationResponse;
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest){
        String email = loginRequest.getEmail();

        if (!usernameExists(email)) {
            throw new CustomException(Error.USER_NOT_FOUND);
        }

        Account user = accountRepository.findByUsername(email)
                .orElseThrow(()-> new CustomException(Error.USER_NOT_FOUND));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(Error.ACCOUNT_PASSWORD_NOT_MACHINES);
        }
        var jwt = jwtTokenUtil.generateToken(user);
        var refreshToken = jwtTokenUtil.generateRefreshToken( user);
        return LoginResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }
    @Override
    public LoginResponse generateRefreshToken(RefreshToken token) {
        String username = jwtTokenUtil.extractUsernameToken(token.getToken());
        UserDetails userDetails= ourUserDetailsService.loadUserByUsername(username);

        String jwttoken= jwtTokenUtil.generateToken(userDetails);

        String refreshToken=jwtTokenUtil.generateRefreshToken(userDetails);

        return  LoginResponse.builder()
                .accessToken(jwttoken)
                .refreshToken(jwttoken)
                .build();
    }


    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
    public Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}