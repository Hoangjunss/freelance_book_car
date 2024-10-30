package com.freelance.bookCar.respository.product.tour;

import com.freelance.bookCar.models.product.ticket.Tourism;
import com.freelance.bookCar.models.product.tour.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour,Integer> {

       //@Query(value = "select * from tour where start_location LIKE '%:name%';", nativeQuery = true)
       List<Tour> findAllByStartLocation( String location);

       @Query(value = "select * from tour where name LIKE '%:name%';", nativeQuery = true)
       List<Tour> searchAllByLocation(@Param("name") String name);
}
