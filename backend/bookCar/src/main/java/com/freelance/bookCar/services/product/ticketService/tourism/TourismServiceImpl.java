package com.freelance.bookCar.services.product.ticketService.tourism;

import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.CreateTourismRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.UpdateTourismRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.CreateTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismDetailResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.UpdateTourismResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.ticket.Ticket;
import com.freelance.bookCar.models.product.ticket.Tourism;
import com.freelance.bookCar.respository.product.ticket.TourismRepository;
import com.freelance.bookCar.services.image.ImageService;
import com.freelance.bookCar.services.product.ticketService.ticket.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TourismServiceImpl implements TourismService {
    @Autowired
    private TourismRepository tourismRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TicketService ticketService;
    @Override
    public CreateTourismResponse createTourism(CreateTourismRequest createTourismRequest) {
        log.info("Creating tourism with name: {}", createTourismRequest.getName());

        // Validation
        if (createTourismRequest.getName() == null) {
            throw new CustomException(Error.TOURISM_INVALID_NAME);
        }
        if (createTourismRequest.getLocation() == null) {
            throw new CustomException(Error.TOURISM_INVALID_LOCATION);
        }
        if (createTourismRequest.getDescription() == null) {
            throw new CustomException(Error.TOURISM_INVALID_DESCRIPTION);
        }
        if (createTourismRequest.getRating() < 0) {
            throw new CustomException(Error.TOURISM_INVALID_RATING);
        }

        Tourism tourism = Tourism.builder()
                .id(getGenerationId())
                .name(createTourismRequest.getName())
                .location(createTourismRequest.getLocation())
                .description(createTourismRequest.getDescription())
                .rating(createTourismRequest.getRating())
                .image(imageService.saveImage(createTourismRequest.getImage()))
                .build();

        try {
            return modelMapper.map(tourismRepository.save(tourism), CreateTourismResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while saving tourism: {}", e.getMessage(), e);
            throw new CustomException(Error.TOURISM_UNABLE_TO_SAVE);
        }
    }

    @Override
    public UpdateTourismResponse updateTourism(UpdateTourismRequest updateTourismRequest) {
        log.info("Updating tourism with id: {}", updateTourismRequest.getId());

        if (updateTourismRequest.getId() == null) {
            throw new CustomException(Error.TOURISM_NOT_FOUND);
        }

        Tourism existingTourism = modelMapper.map(findById(updateTourismRequest.getId()), Tourism.class);

        if (updateTourismRequest.getName() != null) {
            existingTourism.setName(updateTourismRequest.getName());
        }
        if (updateTourismRequest.getLocation() != null) {
            existingTourism.setLocation(updateTourismRequest.getLocation());
        }
        if (updateTourismRequest.getDescription() != null) {
            existingTourism.setDescription(updateTourismRequest.getDescription());
        }
        if (updateTourismRequest.getRating() >= 0) {
            existingTourism.setRating(updateTourismRequest.getRating());
        }
        if (updateTourismRequest.getImage() !=null) {
            existingTourism.setImage(imageService.saveImage(updateTourismRequest.getImage()));
        }

        try {
            return modelMapper.map(tourismRepository.save(existingTourism), UpdateTourismResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while updating tourism: {}", e.getMessage(), e);
            throw new CustomException(Error.TOURISM_UNABLE_TO_UPDATE);
        }
    }

    @Override
    public GetTourismResponse findById(Integer id) {
        log.info("Finding tourism with id: {}", id);
        Tourism tourism = tourismRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.TOURISM_NOT_FOUND));

        return modelMapper.map(tourism, GetTourismResponse.class);
    }

    @Override
    public List<GetTourismResponse> getAll() {
        return tourismRepository.findAll().stream().map(tourism -> modelMapper.map(tourism, GetTourismResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetTourismResponse> findLocation(String location) {
        return tourismRepository.findAllByLocation(location).stream().map(tourism -> modelMapper.map(tourism, GetTourismResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetTourismDetailResponse getDetail(Integer id, LocalDateTime date) {
        GetTourismDetailResponse getTourismDetailResponse=modelMapper.map(findById(id), GetTourismDetailResponse.class);
        Ticket ticket=ticketService.findByIdAndByStartDate(id,date);
        //getTourismDetailResponse.setPrice(ticket.getTourPrice());
        return getTourismDetailResponse;
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
