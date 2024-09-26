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
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.UserRepository;
import com.freelance.bookCar.security.JwtTokenUtil;
import com.freelance.bookCar.security.OurUserDetailsService;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    // Create a new user
    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest) {


        if(userRequest.getEmail() == null){
            throw new CustomException(Error.USER_INVALID_EMAIL);
        }
        if(userRequest.getPassword() == null){
            throw new CustomException(Error.USER_INVALID_PASSWORD);
        }

        User user=User.builder()
                .id(getGenerationId())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .name(userRequest.getName())
                .build();
        try {
            return modelMapper.map(userRepository.save(user),CreateUserResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while saving user: {}", e.getMessage(), e);
            throw new CustomException(Error.USER_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in saving user: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

    }
    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest userRequest) {
        User existingUser = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(userRequest.getAddress() != null) {existingUser.setAddress(userRequest.getAddress());}
        if(userRequest.getName() != null) {existingUser.setName(userRequest.getName());}
        if(userRequest.getPhone() != null) {existingUser.setPhone(userRequest.getPhone());}

        try {
            return modelMapper.map(userRepository.save(existingUser), UpdateUserResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while updating user: {}", e.getMessage(), e);
            throw new CustomException(Error.USER_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in updating user: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

    }
    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest) {
        if(usernameExists(registrationRequest.getEmail())){
            throw new CustomException(Error.USER_ALREADY_EXISTS);
        }

        if(registrationRequest.getEmail() == null){
            throw new CustomException(Error.USER_INVALID_EMAIL);
        }
        if(registrationRequest.getName() == null){            throw new CustomException(Error.USER_INVALID_NAME);
        }
        if(registrationRequest.getPassword() == null){
            throw new CustomException(Error.USER_INVALID_PASSWORD);
        }

        CreateUserRequest createUserRequest=CreateUserRequest.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();
        CreateUserResponse createUserResponse=createUser(createUserRequest);




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

        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new CustomException(Error.USER_NOT_FOUND));
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
        return userRepository.findByEmail(username).isPresent();
    }

}
