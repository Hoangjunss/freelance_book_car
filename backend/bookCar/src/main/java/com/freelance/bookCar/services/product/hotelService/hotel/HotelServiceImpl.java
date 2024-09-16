package com.freelance.bookCar.services.product.hotelService.hotel;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.CreateHotelRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.UpdateHotelRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.CreateHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.UpdateHotelResponse;
import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.respository.product.hotel.HotelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateHotelResponse createHotel(CreateHotelRequest createHotelRequest) {
        if (createHotelRequest.getName() == null) {
            // handle error (e.g., throw exception)
        }
        if (createHotelRequest.getLocation() == null) {
            // handle error
        }
        if (createHotelRequest.getContactInfo() == null) {
            // handle error
        }


        Hotel hotelSave = Hotel.builder()
                .id(getGenerationId())
                .name(createHotelRequest.getName())
                .location(createHotelRequest.getLocation())
                .contactInfo(createHotelRequest.getContactInfo())
                .pricePerNight(createHotelRequest.getPricePerNight())
                .status(createHotelRequest.getStatus())
                .rating(createHotelRequest.getRating())
                .build();

        return modelMapper.map(hotelRepository.save(hotelSave), CreateHotelResponse.class);
    }

    @Override
    public UpdateHotelResponse updateHotel(UpdateHotelRequest updateHotelRequest) {
       if(updateHotelRequest.getId()==null){

       }
       if(updateHotelRequest.getContactInfo()==null){

       }
       if(updateHotelRequest.getLocation()==null){

       }
       Hotel hotel=modelMapper.map(findById(updateHotelRequest.getId()), Hotel.class);
       hotel.setContactInfo(updateHotelRequest.getContactInfo());
       hotel.setLocation(updateHotelRequest.getLocation());
       hotel.setName(updateHotelRequest.getName());
       hotel.setPricePerNight(updateHotelRequest.getPricePerNight());
       hotel.setStatus(updateHotelRequest.getStatus());
       return modelMapper.map(hotelRepository.save(hotel), UpdateHotelResponse.class);

    }

    @Override
    public GetHotelResponse findById(Integer id) {
        return modelMapper.map(hotelRepository.findById(id).orElseThrow(), GetHotelResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
