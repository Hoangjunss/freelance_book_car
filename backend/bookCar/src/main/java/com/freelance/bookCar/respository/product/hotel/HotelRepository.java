package com.freelance.bookCar.respository.product.hotel;

import com.freelance.bookCar.models.product.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
}
