package com.freelance.bookCar.respository.product.hotel;

import com.freelance.bookCar.models.product.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    List<Hotel> findAllByLocation(String location);

    @Query(value = " select * from hotel where name LIKE '%ABC%';", nativeQuery = true)
    List<Hotel> searchAllByLocation(String name);
}
