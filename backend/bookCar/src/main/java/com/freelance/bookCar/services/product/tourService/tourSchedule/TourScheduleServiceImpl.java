package com.freelance.bookCar.services.product.tourService.tourSchedule;

import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.CreateTourScheduleRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.UpdateTourScheduleRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.CreateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.UpdateTourScheduleResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.tour.TourSchedule;
import com.freelance.bookCar.respository.product.tour.TourScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TourScheduleServiceImpl implements TourScheduleService  {
    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateTourScheduleResponse createTourSchedule(CreateTourScheduleRequest createTourScheduleRequest) {
        log.info("Create tour schedule");
        if (createTourScheduleRequest.getTimeStartTour() == null) {
            throw new CustomException(Error.TOUR_SCHEDULE_INVALID_START_TIME);
        }
        if (createTourScheduleRequest.getTimeEndTour() == null) {
            throw new CustomException(Error.TOUR_SCHEDULE_INVALID_END_TIME);
        }
        if (createTourScheduleRequest.getIdTour() == null) {
            throw new CustomException(Error.TOUR_SCHEDULE_MISSING_TOUR_ID);
        }
        if (createTourScheduleRequest.getQuantity() == null || createTourScheduleRequest.getQuantity() <= 0) {
            throw new CustomException(Error.TOUR_SCHEDULE_INVALID_QUANTITY);
        }
        if (createTourScheduleRequest.getPriceTour() == null || createTourScheduleRequest.getPriceTour() <= 0) {
            throw new CustomException(Error.TOUR_SCHEDULE_INVALID_PRICE);
        }
        TourSchedule tourSchedule = TourSchedule.builder()
                .id(getGenerationId())
                .timeStartTour(createTourScheduleRequest.getTimeStartTour())
                .timeEndTour(createTourScheduleRequest.getTimeEndTour())
                .idTour(createTourScheduleRequest.getIdTour())
                .priceTour(createTourScheduleRequest.getPriceTour())
                .quantity(createTourScheduleRequest.getQuantity())
                .build();

        try {
            return modelMapper.map(tourScheduleRepository.save(tourSchedule), CreateTourScheduleResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving TourSchedule: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_SCHEDULE_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdateTourScheduleResponse updateTourSchedule(UpdateTourScheduleRequest updateTourScheduleRequest) {
        log.info("Update Tour Schedule by id: {}", updateTourScheduleRequest.getId());
        if (updateTourScheduleRequest.getId() == null) {
            throw new CustomException(Error.TOUR_SCHEDULE_NOT_FOUND);
        }

        // Tìm tour schedule bằng id và ném lỗi nếu không tìm thấy
        TourSchedule tourSchedule = modelMapper.map(findById(updateTourScheduleRequest.getId()), TourSchedule.class);

        if (updateTourScheduleRequest.getTimeStartTour() != null) {
            tourSchedule.setTimeStartTour(updateTourScheduleRequest.getTimeStartTour());
        }
        if (updateTourScheduleRequest.getTimeEndTour() != null) {
            tourSchedule.setTimeEndTour(updateTourScheduleRequest.getTimeEndTour());
        }
        if (updateTourScheduleRequest.getIdTour() != null) {
            tourSchedule.setIdTour(updateTourScheduleRequest.getIdTour());
        }
        if (updateTourScheduleRequest.getQuantity() != null) {
            tourSchedule.setQuantity(updateTourScheduleRequest.getQuantity());
        }
        if (updateTourScheduleRequest.getPriceTour() != null) {
            tourSchedule.setPriceTour(updateTourScheduleRequest.getPriceTour());
        }

        try {
            return modelMapper.map(tourScheduleRepository.save(tourSchedule), UpdateTourScheduleResponse.class);
        }  catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating TourSchedule: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_SCHEDULE_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

    }

    @Override
    public GetTourScheduleResponse findById(Integer id) {
        return modelMapper.map(tourScheduleRepository.findById(id).orElseThrow(
                () -> new CustomException(Error.TOUR_SCHEDULE_NOT_FOUND)), GetTourScheduleResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}

