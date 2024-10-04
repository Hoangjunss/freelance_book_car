package com.freelance.bookCar.services.user.userInfoService;

import com.freelance.bookCar.dto.request.user.userInfoDTO.CreateUserInfoRequest;
import com.freelance.bookCar.dto.request.user.userInfoDTO.UpdateUserInfoRequest;
import com.freelance.bookCar.dto.response.user.userInfoDTO.CreateUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userInfoDTO.GetUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userInfoDTO.UpdateUserInforesponse;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.user.UserInfo;
import com.freelance.bookCar.respository.user.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserInfoServiceImpl implements  UserInfoService{
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CreateUserInfoResponse create(CreateUserInfoRequest createUserInfoRequest) {
        log.info("CreateUserInfoResponse 28: {}", createUserInfoRequest.toString());
        UserInfo.UserInfoBuilder userInfoBuilder = UserInfo.builder().id(getGenerationId());

        if (createUserInfoRequest.getFirstName() != null) {
            userInfoBuilder.firstName(createUserInfoRequest.getFirstName());
        }

        if (createUserInfoRequest.getLastName() != null) {
            userInfoBuilder.LastName(createUserInfoRequest.getLastName());
        }

        if (createUserInfoRequest.getPhone() != null) {
            userInfoBuilder.phone(createUserInfoRequest.getPhone());
        }

        if (createUserInfoRequest.getEmail() != null) {
            userInfoBuilder.email(createUserInfoRequest.getEmail());
        }

        if (createUserInfoRequest.getAddress() != null) {
            userInfoBuilder.address(createUserInfoRequest.getAddress());
        }
        userInfoBuilder.booking(createUserInfoRequest.getBooking());

        UserInfo userInfo = userInfoBuilder.build();
        return modelMapper.map(userInfoRepository.save(userInfo), CreateUserInfoResponse.class);
    }


    @Override
    public UpdateUserInforesponse update(UpdateUserInfoRequest updateUserInfoRequest) {

        UserInfo userInfo = userInfoRepository.findById(updateUserInfoRequest.getId()).orElseThrow();
        UserInfo.UserInfoBuilder userInfoBuilder = UserInfo.builder().id(userInfo.getId());

        if (updateUserInfoRequest.getFirstName() != null) {
            userInfoBuilder.firstName(updateUserInfoRequest.getFirstName());
        }

        if (updateUserInfoRequest.getLastName() != null) {
            userInfoBuilder.LastName(updateUserInfoRequest.getLastName());
        }

        if (updateUserInfoRequest.getPhone() != null) {
            userInfoBuilder.phone(updateUserInfoRequest.getPhone());
        }

        if (updateUserInfoRequest.getEmail() != null) {
            userInfoBuilder.email(updateUserInfoRequest.getEmail());
        }

        if (updateUserInfoRequest.getAddress() != null) {
            userInfoBuilder.address(updateUserInfoRequest.getAddress());
        }
        userInfo = userInfoBuilder.build();
        return modelMapper.map(userInfoRepository.save(userInfo), UpdateUserInforesponse.class);
    }

    @Override
    public GetUserInfoResponse findById(Integer id) {
        return modelMapper.map(userInfoRepository
                        .findById(id).orElseThrow(),
                GetUserInfoResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
