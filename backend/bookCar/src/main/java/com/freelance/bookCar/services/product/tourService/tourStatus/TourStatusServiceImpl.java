package com.freelance.bookCar.services.product.tourService.tourStatus;

import com.freelance.bookCar.dto.request.product.tourDTO.tourStatus.CreateTourStatusRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourStatus.UpdateTourStatusRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.CreateTourStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.GetTourStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourStatus.UpdateTourStatusResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.tour.TourStatus;
import com.freelance.bookCar.respository.product.tour.TourStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TourStatusServiceImpl implements TourStatusService{
    @Autowired
    private TourStatusRepository tourStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateTourStatusResponse create(CreateTourStatusRequest createTourStatusRequest) {
        log.info("Creating tour status");
        if (createTourStatusRequest.getName() == null || createTourStatusRequest.getName().isEmpty()) {
            throw new CustomException(Error.TOUR_STATUS_INVALID_NAME);
        }

        TourStatus existingTourStatus = tourStatusRepository.findByName(createTourStatusRequest.getName());
        if (existingTourStatus != null) {
            throw new CustomException(Error.TOUR_STATUS_ALREADY_EXISTS);
        }

        TourStatus tourStatusSave = TourStatus.builder()
                .id(getGenerationId())
                .name(createTourStatusRequest.getName())
                .build();

        try {
            return modelMapper.map(tourStatusRepository.save(tourStatusSave), CreateTourStatusResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving TourStatus: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_STATUS_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdateTourStatusResponse update(UpdateTourStatusRequest updateTourStatusRequest) {
        log.info("Updating tour status with id: {}", updateTourStatusRequest.getId());

        if (updateTourStatusRequest.getId() == null) {
            throw new CustomException(Error.TOUR_STATUS_NOT_FOUND);
        }

        TourStatus tourStatus = modelMapper.map(findById(updateTourStatusRequest.getId()), TourStatus.class);

        if (updateTourStatusRequest.getName() != null && !updateTourStatusRequest.getName().isEmpty()) {
            tourStatus.setName(updateTourStatusRequest.getName());
        }

        try {
            return modelMapper.map(tourStatusRepository.save(tourStatus), UpdateTourStatusResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating TourStatus: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_STATUS_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public GetTourStatusResponse findById(Integer id) {
        log.info("Finding tour status by id: {}", id);

        TourStatus tourStatus = tourStatusRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.TOUR_STATUS_NOT_FOUND));

        return modelMapper.map(tourStatus, GetTourStatusResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
