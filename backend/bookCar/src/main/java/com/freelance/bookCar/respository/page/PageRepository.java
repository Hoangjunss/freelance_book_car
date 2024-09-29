package com.freelance.bookCar.respository.page;

import com.freelance.bookCar.models.page.Page;
import com.freelance.bookCar.models.page.TypePage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page,Integer> {

    List<Page> findAllByType(TypePage type);

    Page findByType(TypePage type);
}
