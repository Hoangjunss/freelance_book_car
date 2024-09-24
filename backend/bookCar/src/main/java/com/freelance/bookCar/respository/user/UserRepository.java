package com.freelance.bookCar.respository.user;

import com.freelance.bookCar.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
