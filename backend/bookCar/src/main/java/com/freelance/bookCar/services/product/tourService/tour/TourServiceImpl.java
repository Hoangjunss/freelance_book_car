package com.freelance.bookCar.services.product.tourService.tour;

import com.freelance.bookCar.dto.request.product.tourDTO.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourDetailResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tour.UpdateTourResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.tour.Tour;
import com.freelance.bookCar.models.product.tour.TourSchedule;
import com.freelance.bookCar.respository.product.tour.TourRepository;
import com.freelance.bookCar.services.image.ImageService;
import com.freelance.bookCar.services.product.tourService.tourSchedule.TourScheduleService;
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
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImageService imageService;
    @Autowired
    private TourScheduleService tourScheduleService;
    @Override
    public CreateTourResponse createTour(CreateTourRequest createTourRequest) {
        log.info("Create tour");
        if (createTourRequest.getName() == null || createTourRequest.getName().isEmpty()) {
            throw new CustomException(Error.TOUR_INVALID_NAME);
        }
        if (createTourRequest.getDescription() == null || createTourRequest.getDescription().isEmpty()) {
            throw new CustomException(Error.TOUR_INVALID_DESCRIPTION);
        }
        if (createTourRequest.getEndLocation() == null || createTourRequest.getEndLocation().isEmpty()) {
            throw new CustomException(Error.TOUR_INVALID_END_LOCATION);
        }
        if (createTourRequest.getStartLocation() == null || createTourRequest.getStartLocation().isEmpty()) {
            throw new CustomException(Error.TOUR_INVALID_START_LOCATION);
        }
        if(createTourRequest.getIsActive() == null){
            throw new CustomException(Error.TOUR_INVALID_ID_TOUR_STATUS);
        }

        Tour tourSave = Tour.builder()
                .id(getGenerationId())
                .description(createTourRequest.getDescription())
                .startLocation(createTourRequest.getStartLocation())
                .name(createTourRequest.getName())
                .isActive(createTourRequest.getIsActive())
                .image(imageService.saveImage(createTourRequest.getImage()))
                .build();

        try {
            return modelMapper.map(tourRepository.save(tourSave), CreateTourResponse.class);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving Tour: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_SCHEDULE_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public UpdateTourResponse updateTour(UpdateTourRequest updateTourRequest) {
        log.info("Update Tour by id: {}", updateTourRequest.getId());
        if (updateTourRequest.getId() == null) {
            throw new CustomException(Error.TOUR_NOT_FOUND);
        }

        Tour tour = modelMapper.map(findById(updateTourRequest.getId()), Tour.class);

        if(updateTourRequest.getDescription()!=null){
            tour.setDescription(updateTourRequest.getDescription());
        }
        if(updateTourRequest.getName()!=null){
            tour.setName(updateTourRequest.getName());
        }
        if(updateTourRequest.getStartLocation()!=null){
            tour.setStartLocation(updateTourRequest.getStartLocation());
        }
        if(updateTourRequest.getIsActive()!=null){
            tour.setIsActive(updateTourRequest.getIsActive());
        }
        if(updateTourRequest.getImage()!=null){
            tour.setImage(imageService.saveImage(updateTourRequest.getImage()));
        }

        try {
            return modelMapper.map(tourRepository.save(tour), UpdateTourResponse.class);
        }  catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while updating Tour: {}", e.getMessage(), e);
            throw new CustomException(Error.TOUR_SCHEDULE_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
   
    public GetTourResponse findById(Integer id){
        return modelMapper.map(tourRepository.findById(id).orElseThrow(()
        -> new CustomException(Error.TOUR_NOT_FOUND)), GetTourResponse.class);
    }

    @Override
    public List<GetTourResponse> getAll() {
        return tourRepository.findAll().stream().map(tour -> modelMapper.map(tour, GetTourResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetTourResponse> getLocation(String location) {
        return tourRepository.findAllByStartLocation(location).stream().map(tour -> modelMapper.map(tour, GetTourResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetTourDetailResponse getDetail(Integer id, LocalDateTime dateTime) {
        GetTourDetailResponse getTourDetailResponse=modelMapper.map(findById(id), GetTourDetailResponse.class);
        TourSchedule tourSchedule=tourScheduleService.findByIdAndByStartDate(id,dateTime);

        return getTourDetailResponse;
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

}
