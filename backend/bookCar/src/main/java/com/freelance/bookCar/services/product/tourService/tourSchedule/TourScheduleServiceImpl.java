package com.freelance.bookCar.services.product.tourService.tourSchedule;

import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.CreateTourScheduleRequest;
import com.freelance.bookCar.dto.request.product.tourDTO.tourSchedule.UpdateTourScheduleRequest;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.CreateTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.GetTourScheduleResponse;
import com.freelance.bookCar.dto.response.product.tourDTO.tourSchedule.UpdateTourScheduleResponse;
import com.freelance.bookCar.models.product.tour.TourSchedule;
import com.freelance.bookCar.respository.product.tour.TourScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TourScheduleServiceImpl implements TourScheduleService  {
    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateTourScheduleResponse createTourSchedule(CreateTourScheduleRequest createTourScheduleRequest) {
        if (createTourScheduleRequest.getTimeStartTour() == null) {

        }
        if (createTourScheduleRequest.getTimeEndTour() == null) {

        }
        if (createTourScheduleRequest.getIdTour() == null) {

        }
        if (createTourScheduleRequest.getQuantity() == null) {

        }
        if (createTourScheduleRequest.getPriceTour() == null) {

        }


        TourSchedule tourSchedule = TourSchedule.builder()
                .id(getGenerationId())
                .timeStartTour(createTourScheduleRequest.getTimeStartTour())
                .timeEndTour(createTourScheduleRequest.getTimeEndTour())
                .idTour(createTourScheduleRequest.getIdTour())
                .priceTour(createTourScheduleRequest.getPriceTour())
                .quantity(createTourScheduleRequest.getQuantity())
                .build();

        return modelMapper.map(tourScheduleRepository.save(tourSchedule), CreateTourScheduleResponse.class);
    }

    @Override
    public UpdateTourScheduleResponse updateTourSchedule(UpdateTourScheduleRequest updateTourScheduleRequest) {
        if (updateTourScheduleRequest.getId() == null) {

        }
        if (updateTourScheduleRequest.getTimeStartTour() == null) {

        }
        if (updateTourScheduleRequest.getTimeEndTour() == null) {

        }

        TourSchedule tourSchedule = modelMapper.map(findById(updateTourScheduleRequest.getId()), TourSchedule.class);
        tourSchedule.setTimeStartTour(updateTourScheduleRequest.getTimeStartTour());
        tourSchedule.setTimeEndTour(updateTourScheduleRequest.getTimeEndTour());
        tourSchedule.setIdTour(updateTourScheduleRequest.getIdTour());
        tourSchedule.setPriceTour(updateTourScheduleRequest.getPriceTour());
        tourSchedule.setQuantity(updateTourScheduleRequest.getQuantity());
        return modelMapper.map(tourScheduleRepository.save(tourSchedule), UpdateTourScheduleResponse.class);
    }

    @Override
    public GetTourScheduleResponse findById(Integer id) {
        return modelMapper.map(tourScheduleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Schedule not found")), GetTourScheduleResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}

