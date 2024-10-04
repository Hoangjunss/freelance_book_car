package com.freelance.bookCar.respository.user;

import com.freelance.bookCar.models.user.UserJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJoinRepository extends JpaRepository<UserJoin,Integer> {
    @Query("SELECT ui FROM UserInfo ui WHERE ui.booking.id = :bookingId")
    List<UserJoin> findUserJoinByBookingId(@Param("bookingId") Integer bookingId);
}
