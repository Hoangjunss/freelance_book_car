package com.freelance.bookCar.respository.user;

import com.freelance.bookCar.models.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    @Query("SELECT ui FROM UserInfo ui WHERE ui.booking.id = :bookingId")
    List<UserInfo> findUserInfoByBookingId(@Param("bookingId") Integer bookingId);

}
