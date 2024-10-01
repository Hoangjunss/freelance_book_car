package com.freelance.bookCar.respository.user;

import com.freelance.bookCar.models.user.UserJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJoinRepository extends JpaRepository<UserJoin,Integer> {
}
