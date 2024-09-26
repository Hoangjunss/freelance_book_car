package com.freelance.bookCar.respository.product.tour;

import com.freelance.bookCar.models.product.tour.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TourScheduleRepository extends JpaRepository<TourSchedule,Integer> {
    TourSchedule findByIdAndByStartDate(Integer id, LocalDateTime date);
}
