package com.freelance.bookCar.respository.product;

import com.freelance.bookCar.models.product.tour.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourScheduleRepository extends JpaRepository<TourSchedule,Integer> {
}
