package com.freelance.bookCar.respository.product.voucher;

import com.freelance.bookCar.models.product.voucher.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Voucher findByName(String name);
}
