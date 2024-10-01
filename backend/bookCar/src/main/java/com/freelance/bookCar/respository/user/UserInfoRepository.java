package com.freelance.bookCar.respository.user;

import com.freelance.bookCar.models.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
}
