package com.freelance.bookCar.services.user.userservice;

import com.freelance.bookCar.dto.request.user.accountDTO.LoginRequest;
import com.freelance.bookCar.dto.request.user.accountDTO.LoginTokenGoogle;
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
import com.freelance.bookCar.models.user.TypeUser;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.UserRepository;
import com.freelance.bookCar.security.JwtTokenUtil;
import com.freelance.bookCar.security.OurUserDetailsService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
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
    @Value("${google.clientId}")
    String googleClientId;

    @Value("${secretPsw}")
    String secretPsw;

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
                .type(TypeUser.USER)
                .build();
        try {
            CreateUserResponse createUserResponse=modelMapper.map(userRepository.save(user),CreateUserResponse.class);
            createUserResponse.setType(user.getType().name());
            return createUserResponse;
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



        if(registrationRequest.getName() == null){
            throw new CustomException(Error.USER_INVALID_NAME);
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
    public LoginResponse loginGoogle(LoginTokenGoogle loginTokenGoogle) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                        .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), loginTokenGoogle.getToknen());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        User user=new User();
        LoginResponse loginResponse=new LoginResponse();
        if(usernameExists(payload.getEmail())) {
            user = findByUser(payload.getEmail());

        }
        else{
         RegistrationRequest registrationRequest=   RegistrationRequest.builder()
                    .name((String)payload.get("name"))
                    .email(payload.getEmail())
                    .password(passwordEncoder.encode(secretPsw))
                    .build();
          RegistrationResponse registrationResponse=  registration(registrationRequest);
          user=findByUser(registrationResponse.getEmail());
        }
        loginResponse = loginGoogleByUser(user);

        return loginResponse;
    }
    private LoginResponse loginGoogleByUser(User user){

        var jwt = jwtTokenUtil.generateToken(user);
        var refreshToken = jwtTokenUtil.generateRefreshToken( user);
        return LoginResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
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

        String accessToken = jwtTokenUtil.generateToken(userDetails);

        String refreshToken=jwtTokenUtil.generateRefreshToken(userDetails);

        return  LoginResponse.builder()
                .accessToken(accessToken )
                .refreshToken(refreshToken)
                .build();
    }


    private boolean usernameExists(String username) {
        return userRepository.findByEmail(username).isPresent();
    }
    private User findByUser(String username) {
        return userRepository.findByEmail(username).orElseThrow();
    }

}
