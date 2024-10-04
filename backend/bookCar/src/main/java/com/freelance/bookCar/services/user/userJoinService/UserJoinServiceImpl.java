package com.freelance.bookCar.services.user.userJoinService;

import com.freelance.bookCar.dto.request.user.userJoinDTO.CreateUserJoinRequest;
import com.freelance.bookCar.dto.request.user.userJoinDTO.UpdateUserJoinRequest;
import com.freelance.bookCar.dto.response.user.userInfoDTO.UpdateUserInforesponse;
import com.freelance.bookCar.dto.response.user.userJoinDTO.CreateUserJoinResponse;
import com.freelance.bookCar.dto.response.user.userJoinDTO.GetUserJoinResponse;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.user.UserJoin;
import com.freelance.bookCar.respository.user.UserJoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserJoinServiceImpl implements UserJoinService{
    @Autowired
    private UserJoinRepository userJoinRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateUserJoinResponse create(CreateUserJoinRequest createUserJoinRequest) {
        log.info("CreateUserJoinResponse 27: {}", createUserJoinRequest.toString());
        UserJoin.UserJoinBuilder userJoinBuilder = UserJoin.builder().id(getGenerationId());

        if (createUserJoinRequest.getFirstName() != null) {
            userJoinBuilder.firstName(createUserJoinRequest.getFirstName());
        }

        if (createUserJoinRequest.getLastName() != null) {
            userJoinBuilder.LastName(createUserJoinRequest.getLastName());
        }

        if (createUserJoinRequest.getPhone() != null) {
            userJoinBuilder.phone(createUserJoinRequest.getPhone());
        }

        if (createUserJoinRequest.getEmail() != null) {
            userJoinBuilder.email(createUserJoinRequest.getEmail());
        }

        userJoinBuilder.booking(createUserJoinRequest.getBooking());

        UserJoin userJoin = userJoinBuilder.build();
        return modelMapper.map(userJoinRepository.save(userJoin), CreateUserJoinResponse.class);
    }

    @Override
    public UpdateUserInforesponse update(UpdateUserJoinRequest updateUserJoinRequest) {
        UserJoin userJoin = userJoinRepository.findById(updateUserJoinRequest.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserJoin.UserJoinBuilder userJoinBuilder = UserJoin.builder().id(userJoin.getId());

        if (updateUserJoinRequest.getFirstName() != null) {
            userJoinBuilder.firstName(updateUserJoinRequest.getFirstName());
        }

        if (updateUserJoinRequest.getLastName() != null) {
            userJoinBuilder.LastName(updateUserJoinRequest.getLastName());
        }

        if (updateUserJoinRequest.getPhone() != null) {
            userJoinBuilder.phone(updateUserJoinRequest.getPhone());
        }

        if (updateUserJoinRequest.getEmail() != null) {
            userJoinBuilder.email(updateUserJoinRequest.getEmail());
        }

        userJoin = userJoinBuilder.build();
        UserJoin userJoin1=userJoinRepository.save(userJoin);
        log.info("userjoin78: {}", userJoin1.toString());
        return modelMapper.map(userJoin1, UpdateUserInforesponse.class);
    }

    @Override
    public GetUserJoinResponse findById(Integer id) {
        return modelMapper.map(userJoinRepository
                        .findById(id).orElseThrow(() -> new RuntimeException("User not found")),
                GetUserJoinResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
