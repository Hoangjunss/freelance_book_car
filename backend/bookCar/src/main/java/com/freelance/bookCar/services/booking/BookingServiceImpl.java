package com.freelance.bookCar.services.booking;

import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.request.booking.bookingHotel.AddBookingHotelRequest;
import com.freelance.bookCar.dto.request.booking.bookingHotel.UpdateBookingHotelRequest;
import com.freelance.bookCar.dto.request.booking.bookingTour.AddBookingTourRequest;
import com.freelance.bookCar.dto.request.booking.bookingTour.UpdateBookingTourRequest;
import com.freelance.bookCar.dto.request.booking.bookingTourism.AddBookingTourismRequest;
import com.freelance.bookCar.dto.request.booking.bookingTourism.UpdateBookingTourismRequest;
import com.freelance.bookCar.dto.response.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.booking.UpdateBookingResponse;
import com.freelance.bookCar.dto.response.booking.bookingHotel.AddBookingHotelResponse;
import com.freelance.bookCar.dto.response.booking.bookingHotel.UpdateBookingHotelResponse;
import com.freelance.bookCar.dto.response.booking.bookingTour.AddBookingTourResponse;
import com.freelance.bookCar.dto.response.booking.bookingTour.UpdateBookingTourResponse;
import com.freelance.bookCar.dto.response.booking.bookingTourism.AddBookingTourismResponse;
import com.freelance.bookCar.dto.response.booking.bookingTourism.UpdateBookingTourismResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.booking.BookingDetail;
import com.freelance.bookCar.models.booking.TypeBooking;
import com.freelance.bookCar.models.product.hotel.HotelBooking;
import com.freelance.bookCar.models.product.ticket.Ticket;
import com.freelance.bookCar.models.product.ticket.Tourism;
import com.freelance.bookCar.models.product.tour.Tour;
import com.freelance.bookCar.models.product.tour.TourSchedule;
import com.freelance.bookCar.respository.booking.BookingDetailRepository;
import com.freelance.bookCar.respository.booking.BookingRepository;
import com.freelance.bookCar.services.product.hotelService.hotelBooking.HotelBookingService;
import com.freelance.bookCar.services.product.ticketService.ticket.TicketService;
import com.freelance.bookCar.services.product.ticketService.tourism.TourismService;
import com.freelance.bookCar.services.product.tourService.tour.TourService;
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
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private TourScheduleService tourScheduleService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private HotelBookingService hotelBookingService;

    @Override
    public CreateBookingResponse create(CreateBookingRequest createBookingRequest) {
        log.info("Creating booking for user: {}", createBookingRequest.getIdUser());

        // Validation
        if (createBookingRequest.getIdUser() == null) {
            throw new CustomException(Error.BOOKING_INVALID_ID_USER);
        }
        if(createBookingRequest.getPaymentMethod() == null) {
            throw new CustomException(Error.BOOKING_INVALID_PAYMENT_METHOD);
        }

        if (createBookingRequest.getTotalPrice() < 0D){
            throw new CustomException(Error.BOOKING_INVALID_TOTAL_PRICE);
        }

        Booking booking = Booking.builder()
                .id(getGenerationId())
                .dateBook(LocalDateTime.now())
                .totalPrice(createBookingRequest.getTotalPrice())
                .idUser(createBookingRequest.getIdUser())
                .idPayment(createBookingRequest.getPaymentMethod())
                .typeBooking(TypeBooking.PENDING)
                .build();

        try {
            Booking savedBooking = bookingRepository.save(booking);
            CreateBookingResponse createBookingResponse= modelMapper.map(savedBooking, CreateBookingResponse.class);
            createBookingResponse.setType(savedBooking.getTypeBooking().name());
            return createBookingResponse;
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation occurred while saving Booking: {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in Create Booking: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
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
        if (updateBookingRequest.getIdUser() != null) {
            existingBooking.setIdUser(updateBookingRequest.getIdUser());
        }
        if (updateBookingRequest.getPaymentMethod() != null) {
            existingBooking.setIdPayment(updateBookingRequest.getPaymentMethod());
        }


        try {
            Booking updatedBooking = bookingRepository.save(existingBooking);
            UpdateBookingResponse updateBookingResponse=modelMapper.map(updatedBooking, UpdateBookingResponse.class);
            updateBookingResponse.setType(updatedBooking.getTypeBooking().name());
            return updateBookingResponse;
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while updating booking: {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in Create Booking: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public GetBookingResponse findById(Integer id) {
        log.info("Finding booking with id: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.BOOKING_NOT_FOUND));
        return modelMapper.map(booking, GetBookingResponse.class);
    }

    private boolean ExistBooking(Integer id){
        return bookingRepository.findById(id).isPresent();
    }

    @Override
    public AddBookingTourResponse addBookingTour(AddBookingTourRequest addBookingTourRequest) {
        log.info("Adding tour to booking: {}", addBookingTourRequest.getIdBooking());

        if(addBookingTourRequest.getIdTour() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_ID_TOUR);
        }
        if(addBookingTourRequest.getQuantity() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_QUANTITY);
        }
        if(addBookingTourRequest.getTotalPrice() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_TOTAL_PRICE);
        }
        if(addBookingTourRequest.getIdUser() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_ID_USER);
        }

        // Check if the booking exists, create if not
        Booking booking = new Booking();
        if (!ExistBooking(addBookingTourRequest.getIdBooking())) {
            // Create a new booking using Builder and ModelMapper
            CreateBookingRequest createBookingRequest = modelMapper
                    .map(addBookingTourRequest,
                            CreateBookingRequest.class);
            createBookingRequest.setTotalPrice(addBookingTourRequest.getTotalPrice());

            // Default payment method logic can be added here if needed
            CreateBookingResponse createBookingResponse = create(createBookingRequest);

            // Map the created booking response to the Booking entity using ModelMapper
            booking = modelMapper.map(createBookingResponse, Booking.class);
        } else {
            // Retrieve the existing booking using ModelMapper
            booking = modelMapper.map(findById(addBookingTourRequest.getIdBooking()), Booking.class);

            // Update the total price by adding the price of the new tour
            booking = Booking.builder()
                    .id(booking.getId())
                    .dateBook(booking.getDateBook())
                    .totalPrice(booking.getTotalPrice() + addBookingTourRequest.getTotalPrice())
                    .idUser(booking.getIdUser())
                    .idPayment(booking.getIdPayment())
                    .build();

            // Save the updated booking
            try {
                bookingRepository.save(booking);
            } catch (DataIntegrityViolationException e) {
                log.error("Error occurred while saving tour booking details: {}", e.getMessage(), e);
                throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_SAVE);
            } catch (DataAccessException e) {
                log.error("Database access error occurred in saving tour booking details: {}", e.getMessage(), e);
                throw new CustomException(Error.DATABASE_ACCESS_ERROR);
            }

        }

        TourSchedule tour=modelMapper.map(tourScheduleService.findById(addBookingTourRequest.getIdTour()),TourSchedule.class);

        // Create a BookingDetail object using Builder pattern
        BookingDetail bookingDetail = BookingDetail.builder()
                .idBooking(booking.getId())
                .idTour(tour.getId())
                .quantity(addBookingTourRequest.getQuantity())
                .totalPrice(tour.getPriceTour()*addBookingTourRequest.getQuantity())
                .build();

        try {
            // Save the booking detail
            bookingDetailRepository.save(bookingDetail);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while updating booking details: {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in add booking details: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        // Map the updated booking to AddBookingTourResponse using ModelMapper
        AddBookingTourResponse response = modelMapper.map(booking, AddBookingTourResponse.class);
        response.setIdTour(addBookingTourRequest.getIdTour());
        response.setQuantity(addBookingTourRequest.getQuantity());
        response.setTotalPrice(booking.getTotalPrice());

        return response;
    }

    @Override
    public AddBookingTourismResponse addBookingTourism(AddBookingTourismRequest addBookingTourismRequest) {
        log.info("Adding tourism to booking: {}", addBookingTourismRequest.getIdBooking());

        if(addBookingTourismRequest.getIdTourism() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_ID_TOURISM);
        }
        if(addBookingTourismRequest.getQuantity() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_QUANTITY);
        }
        if(addBookingTourismRequest.getTotalPrice() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_TOTAL_PRICE);
        }
        if(addBookingTourismRequest.getIdUser() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_ID_USER);
        }

        // Check if the booking exists, create if not
        Booking booking;
        if (!ExistBooking(addBookingTourismRequest.getIdBooking())) {
            // Create a new booking using Builder and ModelMapper
            CreateBookingRequest createBookingRequest = modelMapper.map(addBookingTourismRequest, CreateBookingRequest.class);
            createBookingRequest.setTotalPrice(addBookingTourismRequest.getTotalPrice());

            // Default payment method logic can be added here if needed
            CreateBookingResponse createBookingResponse = create(createBookingRequest);

            // Map the created booking response to the Booking entity using ModelMapper
            booking = modelMapper.map(createBookingResponse, Booking.class);
        } else {
            // Retrieve the existing booking using ModelMapper
            booking = modelMapper.map(findById(addBookingTourismRequest.getIdBooking()), Booking.class);

            // Update the total price by adding the price of the new tourism
            booking = Booking.builder()
                    .id(booking.getId())
                    .dateBook(booking.getDateBook())
                    .totalPrice(booking.getTotalPrice() + addBookingTourismRequest.getTotalPrice())
                    .idUser(booking.getIdUser())
                    .idPayment(booking.getIdPayment())
                    .build();

            // Save the updated booking
            bookingRepository.save(booking);
        }

        // Get the Tourism entity
        Ticket tourism = modelMapper.map(ticketService.findById(addBookingTourismRequest.getIdTourism()), Ticket.class);

        // Create a BookingDetail for tourism using Builder pattern
        BookingDetail bookingDetail = BookingDetail.builder()
                .idBooking(booking.getId())
                .idTourism(tourism.getId())
                .quantity(addBookingTourismRequest.getQuantity())
                .totalPrice(tourism.getTourPrice() * addBookingTourismRequest.getQuantity())
                .build();

        try {
            // Save the booking detail for tourism
            bookingDetailRepository.save(bookingDetail);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while saving tourism booking detail: {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in saving tourism Booking detail: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        // Map the updated booking to AddBookingTourismResponse using ModelMapper
        AddBookingTourismResponse response = modelMapper.map(booking, AddBookingTourismResponse.class);
        response.setIdTourism(addBookingTourismRequest.getIdTourism());
        response.setQuantity(addBookingTourismRequest.getQuantity());
        response.setTotalPrice(booking.getTotalPrice());

        return response;
    }
    @Override
    public AddBookingHotelResponse addBookingHotel(AddBookingHotelRequest addBookingHotelRequest) {
        log.info("Adding hotel to booking: {}", addBookingHotelRequest.getIdBooking());

        if(addBookingHotelRequest.getIdHotel() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_ID_HOTEL);
        }
        if(addBookingHotelRequest.getQuantity() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_QUANTITY);
        }
        if(addBookingHotelRequest.getTotalPrice() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_TOTAL_PRICE);
        }
        if(addBookingHotelRequest.getIdUser() == null){
            throw new CustomException(Error.BOOKING_DETAIL_INVALID_ID_USER);
        }

        // Check if the booking exists, create if not
        Booking booking;
        if (!ExistBooking(addBookingHotelRequest.getIdBooking())) {
            // Create a new booking using Builder and ModelMapper
            CreateBookingRequest createBookingRequest = modelMapper.map(addBookingHotelRequest, CreateBookingRequest.class);
            createBookingRequest.setTotalPrice(addBookingHotelRequest.getTotalPrice());

            // Default payment method logic can be added here if needed
            CreateBookingResponse createBookingResponse = create(createBookingRequest);

            // Map the created booking response to the Booking entity using ModelMapper
            booking = modelMapper.map(createBookingResponse, Booking.class);
        } else {
            // Retrieve the existing booking using ModelMapper
            booking = modelMapper.map(findById(addBookingHotelRequest.getIdBooking()), Booking.class);

            // Update the total price by adding the price of the new hotel stay
            booking = Booking.builder()
                    .id(booking.getId())
                    .dateBook(booking.getDateBook())
                    .totalPrice(booking.getTotalPrice() + addBookingHotelRequest.getTotalPrice())
                    .idUser(booking.getIdUser())
                    .idPayment(booking.getIdPayment())
                    .build();

            // Save the updated booking
            bookingRepository.save(booking);
        }

        // Get the Hotel entity
        HotelBooking hotel = modelMapper.map(hotelBookingService.findById(addBookingHotelRequest.getIdHotel()), HotelBooking.class);

        // Create a BookingDetail for hotel using Builder pattern
        BookingDetail bookingDetail = BookingDetail.builder()
                .idBooking(booking.getId())
                .idHotel(hotel.getId())
                .quantity(addBookingHotelRequest.getQuantity())
                .totalPrice(hotel.getTotalPrice() * addBookingHotelRequest.getQuantity())
                .build();

        try {
            // Save the booking detail for hotel
            bookingDetailRepository.save(bookingDetail);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while saving hotel booking detail : {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_SAVE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in saving hotel Booking detail: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        // Map the updated booking to AddBookingHotelResponse using ModelMapper
        AddBookingHotelResponse response = modelMapper.map(booking, AddBookingHotelResponse.class);
        response.setIdHotel(addBookingHotelRequest.getIdHotel());
        response.setQuantity(addBookingHotelRequest.getQuantity());
        response.setTotalPrice(booking.getTotalPrice());

        return response;
    }
    @Override
    public UpdateBookingTourismResponse updateBookingTourism(UpdateBookingTourismRequest updateBookingTourismRequest) {
        log.info("Updating tourism in booking: {}", updateBookingTourismRequest.getIdBooking());

        // Fetch the BookingDetail for the tourism entry
        BookingDetail bookingDetail = bookingDetailRepository.findById(updateBookingTourismRequest.getIdBooking())
                .orElseThrow(() -> new CustomException(Error.BOOKING_DETAIL_NOT_FOUND));

        // Update the quantity and recalculate the total price for the tourism
        if(updateBookingTourismRequest.getQuantity() != null){
            bookingDetail.setQuantity(updateBookingTourismRequest.getQuantity());
        }

        // Fetch the tourism ticket to recalculate the price
        Ticket tourism = modelMapper.map(ticketService.findById(bookingDetail.getIdTourism()), Ticket.class);
        bookingDetail.setTotalPrice(tourism.getTourPrice() * updateBookingTourismRequest.getQuantity());

            // Save the updated booking detail
            bookingDetailRepository.save(bookingDetail);

            // Fetch the Booking to update the total price
            Booking booking = bookingRepository.findById(bookingDetail.getIdBooking())
                    .orElseThrow(() -> new CustomException(Error.BOOKING_NOT_FOUND));

            // Recalculate the total price by summing all BookingDetail entries related to this booking
            Double newTotalPrice = bookingDetailRepository.sumTotalPriceByBookingId(booking.getId());
            booking.setTotalPrice(newTotalPrice);

            // Save the updated booking with the new total price
        try {
            bookingRepository.save(booking);
        }  catch (DataIntegrityViolationException e) {
            log.error("Error occurred while updating tourism booking detail : {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in updating tourism Booking detail: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        // Map and return the response
        UpdateBookingTourismResponse response = modelMapper.map(bookingDetail, UpdateBookingTourismResponse.class);
        response.setIdTourism(bookingDetail.getIdTourism());
        response.setQuantity(bookingDetail.getQuantity());
        response.setTotalPrice(booking.getTotalPrice()); // Updated total price

        return response;
    }
    @Override
    public UpdateBookingHotelResponse updateBookingHotel(UpdateBookingHotelRequest updateBookingHotelRequest) {
        log.info("Updating hotel in booking: {}", updateBookingHotelRequest.getIdBooking());

        // Fetch the BookingDetail for the hotel entry
        BookingDetail bookingDetail = bookingDetailRepository.findById(updateBookingHotelRequest.getIdBooking())
                .orElseThrow(() -> new CustomException(Error.BOOKING_DETAIL_NOT_FOUND));

        // Update the quantity and recalculate the total price for the hotel
        if(updateBookingHotelRequest.getQuantity() != null){
            bookingDetail.setQuantity(updateBookingHotelRequest.getQuantity());
        }

        // Fetch the hotel booking to recalculate the price
        HotelBooking hotel = modelMapper.map(hotelBookingService.findById(bookingDetail.getIdHotel()), HotelBooking.class);
        bookingDetail.setTotalPrice(hotel.getTotalPrice() * updateBookingHotelRequest.getQuantity());

            // Save the updated booking detail
            bookingDetailRepository.save(bookingDetail);

            // Fetch the Booking to update the total price
            Booking booking = bookingRepository.findById(bookingDetail.getIdBooking())
                    .orElseThrow(() -> new CustomException(Error.BOOKING_NOT_FOUND));

            // Recalculate the total price by summing all BookingDetail entries related to this booking
            Double newTotalPrice = bookingDetailRepository.sumTotalPriceByBookingId(booking.getId());
            booking.setTotalPrice(newTotalPrice);

            // Save the updated booking with the new total price
        try {
            bookingRepository.save(booking);
        }  catch (DataIntegrityViolationException e) {
            log.error("Error occurred while updating hotel booking detail : {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in updating hotel Booking detail: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        // Map and return the response
        UpdateBookingHotelResponse response = modelMapper.map(bookingDetail, UpdateBookingHotelResponse.class);
        response.setIdHotel(bookingDetail.getIdHotel());
        response.setQuantity(bookingDetail.getQuantity());
        response.setTotalPrice(booking.getTotalPrice()); // Updated total price

        return response;
    }

    @Override
    public List<GetBookingResponse> getAll() {
      List<Booking> bookingList=bookingRepository.findAll();
      return bookingList.stream().map(booking -> {
          GetBookingResponse getBookingResponse=modelMapper.map(booking, GetBookingResponse.class);
          getBookingResponse.setType(booking.getTypeBooking().name());
          return getBookingResponse;
      }).collect(Collectors.toList());

    }


    @Override
    public UpdateBookingTourResponse updateBookingTour(UpdateBookingTourRequest updateBookingTourRequest) {
        log.info("Updating tour in booking: {}", updateBookingTourRequest.getIdBooking());

        // Fetch the BookingDetail for the tour
        BookingDetail bookingDetail = bookingDetailRepository.findById(updateBookingTourRequest.getIdBooking())
                .orElseThrow(() -> new CustomException(Error.BOOKING_DETAIL_NOT_FOUND));

        // Update the quantity and recalculate the total price for the tour
        if(updateBookingTourRequest.getQuantity() != null){
            bookingDetail.setQuantity(updateBookingTourRequest.getQuantity());
        }

        // Fetch the tour to recalculate the price
        TourSchedule tour = modelMapper.map(tourScheduleService.findById(bookingDetail.getIdTour()), TourSchedule.class);
        bookingDetail.setTotalPrice(tour.getPriceTour() * updateBookingTourRequest.getQuantity());

            // Save the updated booking detail
            bookingDetailRepository.save(bookingDetail);

            // Fetch the Booking to update the total price
            Booking booking = bookingRepository.findById(bookingDetail.getIdBooking())
                    .orElseThrow(() -> new CustomException(Error.BOOKING_NOT_FOUND));

            // Recalculate the total price by summing all BookingDetail entries related to this booking
            Double newTotalPrice = bookingDetailRepository.sumTotalPriceByBookingId(booking.getId());
            booking.setTotalPrice(newTotalPrice);

            // Save the updated booking with the new total price
        try {
            bookingRepository.save(booking);
        } catch (DataIntegrityViolationException e) {
            log.error("Error occurred while updating tour booking detail : {}", e.getMessage(), e);
            throw new CustomException(Error.BOOKING_DETAIL_UNABLE_TO_UPDATE);
        } catch (DataAccessException e) {
            log.error("Database access error occurred in updating tour Booking detail: {}", e.getMessage(), e);
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

        // Map and return the response
        UpdateBookingTourResponse response = modelMapper.map(bookingDetail, UpdateBookingTourResponse.class);
        response.setIdTour(bookingDetail.getIdTour());
        response.setQuantity(bookingDetail.getQuantity());
        response.setTotalPrice(booking.getTotalPrice()); // Updated total price

        return response;
    }


    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
