package com.freelance.bookCar.respository.product.hotel;

import com.freelance.bookCar.models.product.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    List<Hotel> findAllByLocation(String location);
    List<Hotel> searchAllByLocation(String name);
}
