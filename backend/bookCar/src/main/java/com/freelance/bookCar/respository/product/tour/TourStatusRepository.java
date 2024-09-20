package com.freelance.bookCar.respository.product.tour;

import com.freelance.bookCar.models.product.tour.TourStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourStatusRepository extends JpaRepository<TourStatus, Integer> {
    TourStatus findByName(String name);
}
