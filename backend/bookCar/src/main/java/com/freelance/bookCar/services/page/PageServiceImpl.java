package com.freelance.bookCar.services.page;

import com.freelance.bookCar.dto.request.page.CreatePageRequest;
import com.freelance.bookCar.dto.response.page.CreatePageReponse;
import com.freelance.bookCar.dto.response.page.GetPageResponse;
import com.freelance.bookCar.models.page.Page;
import com.freelance.bookCar.models.page.TypePage;
import com.freelance.bookCar.respository.page.PageRepository;
import com.freelance.bookCar.services.image.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private ImageService imageService;
    @Override
    public CreatePageReponse page(CreatePageRequest createPageRequest) {
        if(createPageRequest.getId()==null){
            Page page=Page.builder()
                    .id(getGenerationId())
                    .url(imageService.saveImage(createPageRequest.getFile()))
                    .name(createPageRequest.getName())
                    .description(createPageRequest.getDescription())
                    .type(TypePage.valueOf(createPageRequest.getType())).build();
                return modelMapper.map(pageRepository.save(page), CreatePageReponse.class);


        }
        Page page=pageRepository.findById(createPageRequest.getId()).orElseThrow();
        page.setName(createPageRequest.getName());
        page.setUrl(imageService.saveImage(createPageRequest.getFile()));
        return modelMapper.map(pageRepository.save(page), CreatePageReponse.class);
    }

    @Override
    public GetPageResponse pageHome() {
        return modelMapper.map(pageRepository.findByType(TypePage.HOME), GetPageResponse.class);
    }

    @Override
    public List<GetPageResponse> pageDetail() {
        return pageRepository.findAllByType(TypePage.DETAIL).stream().map(page -> modelMapper.map(page, GetPageResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetPageResponse pageFooter() {
        return modelMapper.map(pageRepository.findByType(TypePage.FOOTER), GetPageResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
