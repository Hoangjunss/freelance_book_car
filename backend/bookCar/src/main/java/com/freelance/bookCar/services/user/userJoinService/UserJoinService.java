package com.freelance.bookCar.services.user.userJoinService;

import com.freelance.bookCar.dto.request.user.userJoinDTO.CreateUserJoinRequest;
import com.freelance.bookCar.dto.request.user.userJoinDTO.UpdateUserJoinRequest;
import com.freelance.bookCar.dto.response.user.userInfoDTO.GetUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userInfoDTO.UpdateUserInforesponse;
import com.freelance.bookCar.dto.response.user.userJoinDTO.CreateUserJoinResponse;
import com.freelance.bookCar.dto.response.user.userJoinDTO.GetUserJoinResponse;
import com.freelance.bookCar.models.booking.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserJoinService {
    CreateUserJoinResponse create(CreateUserJoinRequest createUserInfoRequest);
    UpdateUserInforesponse update(UpdateUserJoinRequest updateUserInfoRequest);
    GetUserJoinResponse findById(Integer id);
    List<GetUserJoinResponse> getUserJoinByBookingId(Integer bookingId);
}
