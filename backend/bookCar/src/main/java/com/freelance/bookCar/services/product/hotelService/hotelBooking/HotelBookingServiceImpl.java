package com.freelance.bookCar.services.product.hotelService.hotelBooking;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.CreateHotelBookingRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.UpdateHotelBookingRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.CreateHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.GetHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.UpdateHotelBookingResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.models.product.hotel.HotelBooking;
import com.freelance.bookCar.respository.product.hotel.HotelBookingRepository;
import com.freelance.bookCar.services.product.hotelService.hotel.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class HotelBookingServiceImpl implements HotelBookingService {
    @Autowired
    private HotelBookingRepository hotelBookingRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CreateHotelBookingResponse createHotelBooking(CreateHotelBookingRequest createHotelBookingRequest) {
        log.info("Creating hotel booking for hotel ID: {}", createHotelBookingRequest.getHotel());

        // Validation
        if (createHotelBookingRequest.getEndDate() == null) {
            throw new CustomException(Error.HOTEL_BOOKING_INVALID_END_DATE);
        }
        if (createHotelBookingRequest.getStartDate() == null) {
            throw new CustomException(Error.HOTEL_BOOKING_INVALID_START_DATE);
        }

        HotelBooking hotelBooking = HotelBooking.builder()
                .id(getGenerationId())
                .hotel(createHotelBookingRequest.getHotel())
                .endDate(createHotelBookingRequest.getEndDate())
                .startDate(createHotelBookingRequest.getStartDate())
                .totalPrice(createHotelBookingRequest.getTotalPrice())
                .build();

        try {
            return modelMapper.map(hotelBookingRepository.save(hotelBooking), CreateHotelBookingResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while saving hotel booking: {}", e.getMessage(), e);
            throw new CustomException(Error.HOTEL_BOOKING_UNABLE_TO_SAVE);
        }
    }

    @Override
    public UpdateHotelBookingResponse updateHotelBooking(UpdateHotelBookingRequest updateHotelBookingRequest) {
        log.info("Updating hotel booking with ID: {}", updateHotelBookingRequest.getId());

        if (updateHotelBookingRequest.getId() == null) {
            throw new CustomException(Error.HOTEL_BOOKING_NOT_FOUND);
        }
        if (updateHotelBookingRequest.getEndDate() == null) {
            throw new CustomException(Error.HOTEL_BOOKING_INVALID_END_DATE);
        }
        if (updateHotelBookingRequest.getStartDate() == null) {
            throw new CustomException(Error.HOTEL_BOOKING_INVALID_START_DATE);
        }

        HotelBooking existingHotelBooking = hotelBookingRepository.findById(updateHotelBookingRequest.getId())
                .orElseThrow(() -> new CustomException(Error.HOTEL_BOOKING_NOT_FOUND));

        hotelService.findById(updateHotelBookingRequest.getHotel()); // Validate hotel existence

        existingHotelBooking.setHotel(updateHotelBookingRequest.getHotel());
        existingHotelBooking.setEndDate(updateHotelBookingRequest.getEndDate());
        existingHotelBooking.setStartDate(updateHotelBookingRequest.getStartDate());
        existingHotelBooking.setTotalPrice(updateHotelBookingRequest.getTotalPrice());

        try {
            return modelMapper.map(hotelBookingRepository.save(existingHotelBooking), UpdateHotelBookingResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while updating hotel booking: {}", e.getMessage(), e);
            throw new CustomException(Error.HOTEL_BOOKING_UNABLE_TO_UPDATE);
        }
    }

    @Override
    public GetHotelBookingResponse findById(Integer id) {
        log.info("Finding hotel booking with ID: {}", id);

        HotelBooking hotelBooking = hotelBookingRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.HOTEL_BOOKING_NOT_FOUND));

        return modelMapper.map(hotelBooking, GetHotelBookingResponse.class);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
