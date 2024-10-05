package com.freelance.bookCar.respository.product.tour;

import com.freelance.bookCar.models.product.tour.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TourScheduleRepository extends JpaRepository<TourSchedule,Integer> {
    TourSchedule findByIdAndTimeStartTour(Integer id, LocalDateTime date);

    List<TourSchedule> findAllByIdTourOrderByTimeStartTourAsc(Integer idTour);
}
