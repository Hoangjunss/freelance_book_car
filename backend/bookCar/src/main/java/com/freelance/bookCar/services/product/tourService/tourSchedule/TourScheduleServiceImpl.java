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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if(createTourScheduleRequest.getIdTourScheduleStatus()==null){
            throw new CustomException(Error.TOUR_SCHEDULE_INVALID_STATUS);
        }
        TourSchedule tourSchedule = TourSchedule.builder()
                .id(getGenerationId())
                .timeStartTour(createTourScheduleRequest.getTimeStartTour())
                .timeStartTour(createTourScheduleRequest.getTimeEndTour())
                .idTour(createTourScheduleRequest.getIdTour())
                .priceTour(createTourScheduleRequest.getPriceTour())
                .idTourScheduleStatus(createTourScheduleRequest.getIdTourScheduleStatus())
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

        TourSchedule tourSchedule = modelMapper.map(findById(updateTourScheduleRequest.getId()), TourSchedule.class);

        if (updateTourScheduleRequest.getTimeStartTour() != null) {
            tourSchedule.setTimeStartTour(updateTourScheduleRequest.getTimeStartTour());
        }
        if (updateTourScheduleRequest.getIdTour() != null) {
            tourSchedule.setIdTour(updateTourScheduleRequest.getIdTour());
        }
        if (updateTourScheduleRequest.getPriceTour() != null) {
            tourSchedule.setPriceTour(updateTourScheduleRequest.getPriceTour());
        }
        if (updateTourScheduleRequest.getIdTourScheduleStatus()!= null) {
            tourSchedule.setIdTourScheduleStatus(updateTourScheduleRequest.getIdTourScheduleStatus());
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

    @Override
    public List<GetTourScheduleResponse> getAll() {
        return tourScheduleRepository.findAll().stream().map(tourSchedule -> modelMapper.map(tourSchedule, GetTourScheduleResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetTourScheduleResponse> findAllByIdTour(Integer idTour) {
        return tourScheduleRepository.findAllByIdTour(idTour).stream().map(tourSchedule -> modelMapper.map(tourSchedule, GetTourScheduleResponse.class)).collect(Collectors.toList());
    }

    @Override
    public TourSchedule findByIdAndByStartDate(Integer id, LocalDateTime date) {
        return tourScheduleRepository.findByIdAndTimeStartTour(id,date);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}

