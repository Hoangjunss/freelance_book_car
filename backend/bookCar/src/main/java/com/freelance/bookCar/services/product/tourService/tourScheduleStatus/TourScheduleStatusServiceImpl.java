package com.freelance.bookCar.services.product.tourService.tourScheduleStatus;

import com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus.CreateTourScheduleStatusRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourScheduleStatus.UpdateTourScheduleStatusRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.CreateTourScheduleStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.GetTourScheduleStatusResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourScheduleStatus.UpdateTourScheduleStatusResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.tour.TourScheduleStatus;
import com.freelance.bookCar.respository.product.tour.TourScheduleStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TourScheduleStatusServiceImpl implements TourScheduleStatusService{
    @Autowired
    private TourScheduleStatusRepository tourScheduleStatusRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateTourScheduleStatusResponse create(CreateTourScheduleStatusRequest createTourScheduleStatusRequest) {
        log.info("Creating tour schedule status");

        if (createTourScheduleStatusRequest.getName() == null || createTourScheduleStatusRequest.getName().isEmpty()) {
            throw new CustomException(Error.TOUR_SCHEDULE_STATUS_INVALID_NAME);
        }

        TourScheduleStatus tourScheduleStatus = TourScheduleStatus.builder()
                .id(getGenerationId())
                .name(createTourScheduleStatusRequest.getName())
                .build();

        try {
            return modelMapper.map(tourScheduleStatusRepository.save(tourScheduleStatus), CreateTourScheduleStatusResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while saving TourScheduleStatus: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_SCHEDULE_STATUS_UNABLE_TO_SAVE);
        }
    }

    @Override
    public UpdateTourScheduleStatusResponse update(UpdateTourScheduleStatusRequest updateTourScheduleStatusRequest) {
        log.info("Updating tour schedule status with id: {}", updateTourScheduleStatusRequest.getId());

        TourScheduleStatus tourScheduleStatus = modelMapper.map(findById(updateTourScheduleStatusRequest.getId()), TourScheduleStatus.class);

        if (updateTourScheduleStatusRequest.getName() != null) {
            tourScheduleStatus.setName(updateTourScheduleStatusRequest.getName());
        }

        try {
            return modelMapper.map(tourScheduleStatusRepository.save(tourScheduleStatus), UpdateTourScheduleStatusResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while updating TourScheduleStatus: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_SCHEDULE_STATUS_UNABLE_TO_UPDATE);
        }
    }

    @Override
    public GetTourScheduleStatusResponse findById(Integer id) {
        log.info("Fetching tour schedule status by id: {}", id);

        TourScheduleStatus tourScheduleStatus = tourScheduleStatusRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.TOUR_SCHEDULE_STATUS_NOT_FOUND));

        return modelMapper.map(tourScheduleStatus, GetTourScheduleStatusResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
