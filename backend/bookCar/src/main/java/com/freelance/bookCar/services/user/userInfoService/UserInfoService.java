package com.freelance.bookCar.services.user.userInfoService;

import com.freelance.bookCar.dto.request.user.userInfoDTO.CreateUserInfoRequest;
import com.freelance.bookCar.dto.request.user.userInfoDTO.UpdateUserInfoRequest;
import com.freelance.bookCar.dto.response.user.userInfoDTO.CreateUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userInfoDTO.GetUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userInfoDTO.UpdateUserInforesponse;
import com.freelance.bookCar.models.booking.Booking;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    CreateUserInfoResponse create(CreateUserInfoRequest createUserInfoRequest);
    UpdateUserInforesponse update(UpdateUserInfoRequest updateUserInfoRequest);
    GetUserInfoResponse findById(Integer id);

}
