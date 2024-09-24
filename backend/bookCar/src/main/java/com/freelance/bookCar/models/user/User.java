package com.freelance.bookCar.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String type;
    @OneToOne(mappedBy = "user")
    private Account account;
    // Getters and Setters
}