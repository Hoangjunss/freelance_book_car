package com.freelance.bookCar.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserJoin {
    @Id
    private Integer id;
    private String firstName;
    private String LastName;
    private String phone;
    private String email;
}
