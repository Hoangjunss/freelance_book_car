package com.freelance.bookCar.dto.response.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePageReponse {
    private Integer id;
    private String name;
    private String url;
    private String type;
    private String description;
}
