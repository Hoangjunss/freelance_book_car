package com.freelance.bookCar.models.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.freelance.bookCar.models.product.voucher.Voucher;
import com.freelance.bookCar.models.user.UserInfo;
import com.freelance.bookCar.models.user.UserJoin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    private Integer id;
    private LocalDateTime dateBook;
    private double totalPrice;
    private Integer idUser;
    private Integer idPayment;
    @ManyToOne(optional = true, targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id", nullable = true)
    private Voucher voucher;
    @Enumerated(EnumType.STRING)
    private TypeBooking typeBooking;
}