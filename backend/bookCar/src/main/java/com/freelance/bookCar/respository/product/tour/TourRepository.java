package com.freelance.bookCar.respository.product.tour;

import com.freelance.bookCar.models.product.ticket.Tourism;
import com.freelance.bookCar.models.product.tour.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour,Integer> {
       List<Tour> findAllByStartLocation(String location);
       List<Tour> searchAllByLocation(String name);
}
