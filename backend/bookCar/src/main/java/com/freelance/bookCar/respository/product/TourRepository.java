package com.freelance.bookCar.respository.product;

import com.freelance.bookCar.models.product.tour.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour,Integer> {

}
