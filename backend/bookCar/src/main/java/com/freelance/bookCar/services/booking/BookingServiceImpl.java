package com.freelance.bookCar.services.booking;

import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.response.product.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.product.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.product.booking.UpdateBookingResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.respository.booking.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CreateBookingResponse create(CreateBookingRequest createBookingRequest) {
        log.info("Creating booking for user: {}", createBookingRequest.getUser());

        // Validation
        if (createBookingRequest.getUser() == null) {
            throw new CustomException(Error.BOOKING_INVALID_ID_USER);
        }
        if(createBookingRequest.getPaymentMethod() == null) {
            throw new CustomException(Error.BOOKING_INVALID_PAYMENT_METHOD);
        }
        if(createBookingRequest.getInvoice() == null){
            throw new CustomException(Error.BOOKING_INVALID_INVOICE);
        }
        if (createBookingRequest.getTotalPrice() < 0D){
            throw new CustomException(Error.BOOKING_INVALID_TOTAL_PRICE);
        }

        Booking booking = Booking.builder()
                .id(getGenerationId())
                .dateBook(LocalDateTime.now())
                .totalPrice(createBookingRequest.getTotalPrice())
                .user(createBookingRequest.getUser())
                .paymentMethod(createBookingRequest.getPaymentMethod())
                .invoice(createBookingRequest.getInvoice())
                .build();

        try {
            Booking savedBooking = bookingRepository.save(booking);
            return modelMapper.map(savedBooking, CreateBookingResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while saving booking: {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_UNABLE_TO_SAVE);
        }
    }

    @Override
    public UpdateBookingResponse update(UpdateBookingRequest updateBookingRequest) {
        log.info("Updating booking with id: {}", updateBookingRequest.getId());

        if (updateBookingRequest.getId() == null) {
            throw new CustomException(Error.BOOKING_NOT_FOUND);
        }

        Booking existingBooking = modelMapper.map(findById(updateBookingRequest.getId()), Booking.class);

        if (updateBookingRequest.getDateBook() != null) {
            existingBooking.setDateBook(updateBookingRequest.getDateBook());
        }
        if (updateBookingRequest.getTotalPrice() >= 0) {
            existingBooking.setTotalPrice(updateBookingRequest.getTotalPrice());
        }
        if (updateBookingRequest.getUser() != null) {
            existingBooking.setUser(updateBookingRequest.getUser());
        }
        if (updateBookingRequest.getPaymentMethod() != null) {
            existingBooking.setPaymentMethod(updateBookingRequest.getPaymentMethod());
        }
        if (updateBookingRequest.getInvoice() != null) {
            existingBooking.setInvoice(updateBookingRequest.getInvoice());
        }

        try {
            Booking updatedBooking = bookingRepository.save(existingBooking);
            return modelMapper.map(updatedBooking, UpdateBookingResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while updating booking: {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_UNABLE_TO_UPDATE);
        }
    }

    @Override
    public GetBookingResponse findById(Integer id) {
        log.info("Finding booking with id: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.BOOKING_NOT_FOUND));
        return modelMapper.map(booking, GetBookingResponse.class);
    }
    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
