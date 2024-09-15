package com.freelance.bookCar.services.product.tour;

import com.freelance.bookCar.dto.request.product.tour.CreateTourRequest;
import com.freelance.bookCar.dto.request.product.tour.UpdateTourRequest;
import com.freelance.bookCar.dto.response.product.tour.CreateTourResponse;
import com.freelance.bookCar.dto.response.product.tour.GetTourResponse;
import com.freelance.bookCar.dto.response.product.tour.UpdateTourResponse;
import com.freelance.bookCar.models.product.tour.Tour;
import com.freelance.bookCar.respository.product.TourRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateTourResponse createTour(CreateTourRequest createTourRequest) {
        if (createTourRequest.getName() == null){
            //
        }
        if (createTourRequest.getDescription() == null){
            //
        }
        if (createTourRequest.getEndLocation() == null){
            //
        }
        if (createTourRequest.getStartLocation()==null){

        }
        Tour tourSave =Tour.builder()
                .id(getGenerationId())
                .description(createTourRequest.getDescription())
                .endLocation(createTourRequest.getEndLocation())
                .startLocation(createTourRequest.getStartLocation())
                .build();
        return modelMapper.map(tourRepository.save(tourSave), CreateTourResponse.class);
    }

    @Override
    public UpdateTourResponse updateTour(UpdateTourRequest updateTourRequest) {
        if(updateTourRequest.getId()==null){

        }
        if(updateTourRequest.getDescription()==null){

        }
        if(updateTourRequest.getEndLocation()==null){

        }
        if(updateTourRequest.getStartLocation()==null){

        }

        if(updateTourRequest.getName()==null){

        }
        Tour tour = modelMapper.map(findById(updateTourRequest.getId()),Tour.class);
        tour.setId(updateTourRequest.getId());
        tour.setDescription(updateTourRequest.getDescription());
        tour.setName(updateTourRequest.getName());
        tour.setEndLocation(updateTourRequest.getEndLocation());
        tour.setStartLocation(updateTourRequest.getStartLocation());
        return modelMapper.map(tourRepository.save(tour), UpdateTourResponse.class);
    }
   public GetTourResponse findById(Integer id){
        return modelMapper.map(tourRepository.findById(id).orElseThrow(), GetTourResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }

}
