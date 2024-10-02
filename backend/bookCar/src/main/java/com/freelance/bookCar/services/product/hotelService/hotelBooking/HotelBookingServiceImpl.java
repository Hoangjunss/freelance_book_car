package com.freelance.bookCar.services.product.hotelService.hotelBooking;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.CreateHotelBookingRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotelBooking.UpdateHotelBookingRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.CreateHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.GetHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotelBooking.UpdateHotelBookingResponse;
import com.freelance.bookCar.dto.response.product.ticketDTO.ticket.GetTicketResponse;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (createHotelBookingRequest.getHotel() == null) {
            throw new CustomException(Error.HOTEL_BOOKING_INVALID_ID_HOTEL);
        }
        if (createHotelBookingRequest.getTotalPrice() < 0D) {
            throw new CustomException(Error.HOTEL_BOOKING_INVALID_TOTAL_PRICE);
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

        HotelBooking existingHotelBooking = modelMapper.map(findById(updateHotelBookingRequest.getId()), HotelBooking.class);

        hotelService.findById(updateHotelBookingRequest.getHotel()); // Validate hotel existence
        if (updateHotelBookingRequest.getEndDate() != null) {
            existingHotelBooking.setEndDate(updateHotelBookingRequest.getEndDate());
        }
        if (updateHotelBookingRequest.getStartDate() != null) {
            existingHotelBooking.setStartDate(updateHotelBookingRequest.getStartDate());
        }
        if (updateHotelBookingRequest.getHotel()!= null) {
            existingHotelBooking.setHotel(updateHotelBookingRequest.getHotel());
        }
        if (updateHotelBookingRequest.getTotalPrice()> 0D) {
            existingHotelBooking.setTotalPrice(updateHotelBookingRequest.getTotalPrice());
        }

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

    @Override
    public List<GetHotelBookingResponse> findAllByIdHotel(Integer idHotel) {
        return hotelBookingRepository.findAllByHotel(idHotel)
                .stream()
                .map(hotelBooking -> modelMapper.map(hotelBooking, GetHotelBookingResponse.class)).collect(Collectors.toList());
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
