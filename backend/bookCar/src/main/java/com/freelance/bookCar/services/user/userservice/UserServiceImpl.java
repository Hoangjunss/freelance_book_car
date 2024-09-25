package com.freelance.bookCar.services.user.userservice;

import com.freelance.bookCar.dto.request.user.userDTO.CreateUserRequest;
import com.freelance.bookCar.dto.request.user.userDTO.UpdateUserRequest;
import com.freelance.bookCar.dto.response.user.userDTO.CreateUserResponse;
import com.freelance.bookCar.dto.response.user.userDTO.UpdateUserResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Create a new user
    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest) {

        if (userRequest.getAddress() == null) {
            throw new CustomException(Error.USER_INVALID_ADDRESS);
        }
        if(userRequest.getPhone() == null) {
            throw new CustomException(Error.USER_INVALID_PHONE);
        }
        if(userRequest.getName() == null){
            throw new CustomException(Error.USER_INVALID_NAME);
        }
        if(userRequest.getEmail() == null){
            throw new CustomException(Error.USER_INVALID_EMAIL);
        }

        User user=User.builder()
                .id(getGenerationId())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
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
}
