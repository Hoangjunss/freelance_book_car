package com.freelance.bookCar.respository.product.ticket;

import com.freelance.bookCar.models.product.ticket.Tourism;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourismRepository extends JpaRepository<Tourism,Integer> {
   List<Tourism> findAllByLocation(String location);
}
