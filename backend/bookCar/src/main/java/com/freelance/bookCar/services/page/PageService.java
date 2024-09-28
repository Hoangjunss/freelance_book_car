package com.freelance.bookCar.services.page;

import com.freelance.bookCar.dto.request.page.CreatePageRequest;
import com.freelance.bookCar.dto.response.page.CreatePageReponse;

public interface PageService {
    CreatePageReponse page(CreatePageRequest createPageRequest);
}
