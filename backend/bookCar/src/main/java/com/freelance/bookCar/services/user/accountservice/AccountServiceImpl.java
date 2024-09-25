package com.freelance.bookCar.services.user.accountservice;

import com.freelance.bookCar.dto.request.user.accountDTO.LoginRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.RefreshToken;
import com.freelance.bookCar.dto.request.user.accountDTO.RegistrationRequest;
import com.freelance.bookCar.dto.request.user.userDTO.CreateUserRequest;
import com.freelance.bookCar.dto.response.user.accountDTO.LoginResponse;
import com.freelance.bookCar.dto.response.user.accountDTO.RegistrationResponse;
import com.freelance.bookCar.dto.response.user.userDTO.CreateUserResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.models.user.Account;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.AccountRepository;
import com.freelance.bookCar.security.JwtTokenUtil;
import com.freelance.bookCar.security.OurUserDetailsService;
import com.freelance.bookCar.services.user.userservice.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

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
                .build();
        return registrationResponse;
    }
    @Override
    private LoginResponse login(LoginRequest loginRequest){
        String email = loginRequest.getEmail();

        // Kiểm tra xem email đã tồn tại chưa
        if (!usernameExists(email)) {
            throw new CustomJwtException(Error.USER_NOT_FOUND);
        }



        Account user = accountRepository.findByUsername(email).orElseThrow();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomJwtException(Error.NOT_FOUND);
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


        // 2. Lấy thông tin từ token cũ
        String username = jwtTokenUtil.extractUsernameToken(token.getToken());

        // 3. Tạo refresh token mới
        UserDetails userDetails= ourUserDetailsService.loadUserByUsername(username);
        String jwttoken= jwtTokenUtil.generateToken(userDetails);
        String refreshToken=jwtTokenUtil.generateRefreshToken(userDetails);
        // 4. Trả về đối tượng phản hồi chứa refresh token
        return  LoginResponse.builder().accessToken(jwttoken).refreshToken(jwttoken).build();
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
