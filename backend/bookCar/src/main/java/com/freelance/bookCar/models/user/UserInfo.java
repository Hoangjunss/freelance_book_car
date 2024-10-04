package com.freelance.bookCar.models.user;

import com.freelance.bookCar.models.booking.Booking;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
