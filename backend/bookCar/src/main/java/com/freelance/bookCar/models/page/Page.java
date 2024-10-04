package com.freelance.bookCar.models.page;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Page {
    @Id
    private  Integer id;
    private String name;
    private String url;
    @Enumerated(EnumType.STRING)
    private TypePage type;
    private String description;
}
