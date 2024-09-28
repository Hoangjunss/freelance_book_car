package com.freelance.bookCar.dto.request.page;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreatePageRequest {
    private Integer id;
    private String name;
    private MultipartFile file;
    private String type;

}
