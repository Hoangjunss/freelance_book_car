package com.freelance.bookCar.services.user.userservice;

import com.freelance.bookCar.dto.request.user.userDTO.CreateUserRequest;
import com.freelance.bookCar.dto.request.user.userDTO.UpdateUserRequest;
import com.freelance.bookCar.dto.response.user.userDTO.CreateUserResponse;
import com.freelance.bookCar.dto.response.user.userDTO.UpdateUserResponse;
import com.freelance.bookCar.models.user.User;
import com.freelance.bookCar.respository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Create a new user
    @Override
    public CreateUserResponse createUser(CreateUserRequest userRequest) {
        User user=User.builder()
                .id(getGenerationId())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .name(userRequest.getName())
                .build();
        return modelMapper.map(userRepository.save(user),CreateUserResponse.class);
    }
    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest userRequest) {
        User existingUser = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setAddress(userRequest.getAddress());
        existingUser.setName(userRequest.getName());
        existingUser.setPhone(userRequest.getPhone());
        return modelMapper.map(userRepository.save(existingUser), UpdateUserResponse.class);
    }
    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
