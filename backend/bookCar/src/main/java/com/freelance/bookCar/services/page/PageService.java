package com.freelance.bookCar.services.page;

import com.freelance.bookCar.dto.request.page.CreatePageRequest;
import com.freelance.bookCar.dto.response.page.CreatePageReponse;
import com.freelance.bookCar.dto.response.page.GetPageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageService {
    CreatePageReponse page(CreatePageRequest createPageRequest);
    GetPageResponse pageHome();
    List<GetPageResponse> pageDetail();
    GetPageResponse pageFooter();
}
