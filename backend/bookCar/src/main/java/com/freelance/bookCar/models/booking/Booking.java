package com.freelance.bookCar.models.booking;

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
    private Integer idVoucher;

    @Enumerated(EnumType.STRING)
    private TypeBooking typeBooking;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<UserJoin> userJoin = new ArrayList<>();;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<UserInfo> userInfo = new ArrayList<>();;
}
