package com.freelance.bookCar.models.user;

import jakarta.persistence.Entity;
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
    private String firstName;
    private String LastName;
    private String phone;
    private String email;
    private String address;
}
