package com.freelance.bookCar.dto.response.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPageResponse {
    private Integer id;
    private String name;
    private String url;
}
