package com.freelance.bookCar.models.user;

import com.freelance.bookCar.models.booking.Booking;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserInfo {
    @Id
    private Integer id;
    private String firstName;
    private String LastName;
    private String phone;
    private String email;
    private String address;
    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Booking booking;
}
