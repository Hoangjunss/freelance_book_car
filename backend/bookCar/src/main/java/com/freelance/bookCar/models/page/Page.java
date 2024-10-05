package com.freelance.bookCar.models.page;

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
public class Page {
    @Id
    private  Integer id;
    private String name;
    private String url;
    @Enumerated(EnumType.STRING)
    private TypePage type;
    @Lob
    private String description;
}
