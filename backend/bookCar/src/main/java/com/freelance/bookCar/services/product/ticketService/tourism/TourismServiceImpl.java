package com.freelance.bookCar.services.product.ticketService.tourism;

import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.CreateTourismRequest;
import com.freelance.bookCar.dto.request.product.ticketDTO.tourism.UpdateTourismRequest;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.CreateTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.GetTourismResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.tourism.UpdateTourismResponse;
import com.freelance.bookCar.models.product.ticket.Tourism;
import com.freelance.bookCar.respository.product.ticket.TourismRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TourismServiceImpl implements TourismService {
    @Autowired
    private TourismRepository tourismRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateTourismResponse createTourism(CreateTourismRequest createTourismRequest) {
        if (createTourismRequest.getName() == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (createTourismRequest.getLocation() == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        if (createTourismRequest.getDescription() == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (createTourismRequest.getRating() <= 0) {
            throw new IllegalArgumentException("Rating must be greater than zero");
        }

        Tourism tourism = Tourism.builder()
                .id(getGenerationId())
                .name(createTourismRequest.getName())
                .location(createTourismRequest.getLocation())
                .description(createTourismRequest.getDescription())
                .rating(createTourismRequest.getRating())
                .build();

        return modelMapper.map(tourismRepository.save(tourism), CreateTourismResponse.class);
    }

    @Override
    public UpdateTourismResponse updateTourism(UpdateTourismRequest updateTourismRequest) {
        if (updateTourismRequest.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Tourism existingTourism = tourismRepository.findById(updateTourismRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Tourism not found"));

        if (updateTourismRequest.getName() != null) {
            existingTourism.setName(updateTourismRequest.getName());
        }
        if (updateTourismRequest.getLocation() != null) {
            existingTourism.setLocation(updateTourismRequest.getLocation());
        }
        if (updateTourismRequest.getDescription() != null) {
            existingTourism.setDescription(updateTourismRequest.getDescription());
        }
        if (updateTourismRequest.getRating() > 0) {
            existingTourism.setRating(updateTourismRequest.getRating());
        }

        return modelMapper.map(tourismRepository.save(existingTourism), UpdateTourismResponse.class);
    }

    @Override
    public GetTourismResponse findById(Integer id) {
        Tourism tourism = tourismRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tourism not found"));

        return modelMapper.map(tourism, GetTourismResponse.class);
    }

    private Long getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & 0xFFFFFFFFFFFFL;
    }
}
