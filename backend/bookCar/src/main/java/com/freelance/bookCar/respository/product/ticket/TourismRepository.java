package com.freelance.bookCar.respository.product.ticket;

import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.models.product.ticket.Tourism;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourismRepository extends JpaRepository<Tourism,Integer> {
   List<Tourism> findAllByLocation(String location);

   @Query(value="select * from tourism where name LIKE '%:name%';", nativeQuery=true)
   List<Tourism> searchAllByLocation(@Param("name") String name);
}
