package com.freelance.bookCar.services.product.hotelService.hotelBooking;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.CreateHotelBookingRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.UpdateHotelBookingRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.CreateHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.GetHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.UpdateHotelBookingResponse;
import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.models.product.hotel.HotelBooking;
import com.freelance.bookCar.respository.product.hotel.HotelBookingRepository;
import com.freelance.bookCar.services.product.hotelService.hotel.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class HotelBookingServiceImpl implements HotelBookingService {
    @Autowired
    private HotelBookingRepository hotelBookingRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateHotelBookingResponse createHotelBooking(CreateHotelBookingRequest createHotelBookingRequest) {
        if(createHotelBookingRequest.getEndDate()==null){

        }
        if(createHotelBookingRequest.getStartDate()==null){

        }
        HotelBooking hotelBooking=HotelBooking.builder()
                .id(getGenerationId())
                .hotel(createHotelBookingRequest.getHotel())
                .endDate(createHotelBookingRequest.getEndDate())
                .startDate(createHotelBookingRequest.getStartDate())
                .totalPrice(createHotelBookingRequest.getTotalPrice())
                .build();
        return modelMapper.map(hotelBookingRepository.save(hotelBooking), CreateHotelBookingResponse.class);
    }

    @Override
    public UpdateHotelBookingResponse updateHotelBooking(UpdateHotelBookingRequest updateHotelBookingRequest) {
        if(updateHotelBookingRequest.getEndDate()==null){

        }
        if(updateHotelBookingRequest.getStartDate()==null){

        }
        HotelBooking hotelBooking=modelMapper.map(findById(updateHotelBookingRequest.getId()),HotelBooking.class);
        hotelService.findById(updateHotelBookingRequest.getHotel());
        hotelBooking.setHotel(updateHotelBookingRequest.getHotel());
        hotelBooking.setEndDate(updateHotelBookingRequest.getEndDate());
        hotelBooking.setStartDate(updateHotelBookingRequest.getStartDate());
        hotelBooking.setTotalPrice(updateHotelBookingRequest.getTotalPrice());
        return modelMapper.map(hotelBookingRepository.save(hotelBooking), UpdateHotelBookingResponse.class);
    }

    @Override
    public GetHotelBookingResponse findById(Integer id) {
        return modelMapper.map(hotelBookingRepository.findById(id).orElseThrow(),GetHotelBookingResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
