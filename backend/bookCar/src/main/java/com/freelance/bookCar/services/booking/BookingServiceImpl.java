package com.freelance.bookCar.services.booking;

import com.freelance.bookCar.dto.request.booking.CreateBookingRequest;
import com.freelance.bookCar.dto.request.booking.OrderRequest;
import com.freelance.bookCar.dto.request.booking.UpdateBookingRequest;
import com.freelance.bookCar.dto.request.booking.bookingHotel.AddBookingHotelRequest;
import com.freelance.bookCar.dto.request.booking.bookingHotel.UpdateBookingHotelRequest;
import com.freelance.bookCar.dto.request.booking.bookingTour.AddBookingTourRequest;
import com.freelance.bookCar.dto.request.booking.bookingTour.UpdateBookingTourRequest;
import com.freelance.bookCar.dto.request.booking.bookingTourism.AddBookingTourismRequest;
import com.freelance.bookCar.dto.request.booking.bookingTourism.UpdateBookingTourismRequest;
import com.freelance.bookCar.dto.request.user.userInfoDTO.CreateUserInfoRequest;
import com.freelance.bookCar.dto.request.user.userJoinDTO.CreateUserJoinRequest;
import com.freelance.bookCar.dto.response.booking.CreateBookingResponse;
import com.freelance.bookCar.dto.response.booking.GetBookingResponse;
import com.freelance.bookCar.dto.response.booking.OrderResponse;
import com.freelance.bookCar.dto.response.booking.UpdateBookingResponse;
import com.freelance.bookCar.dto.response.booking.bookingHotel.AddBookingHotelResponse;
import com.freelance.bookCar.dto.response.booking.bookingHotel.UpdateBookingHotelResponse;
import com.freelance.bookCar.dto.response.booking.bookingTour.AddBookingTourResponse;
import com.freelance.bookCar.dto.response.booking.bookingTour.UpdateBookingTourResponse;
import com.freelance.bookCar.dto.response.booking.bookingTourism.AddBookingTourismResponse;
import com.freelance.bookCar.dto.response.booking.bookingTourism.UpdateBookingTourismResponse;
import com.freelance.bookCar.dto.response.bookingDetail.GetBookingDetailResponse;
import com.freelance.bookCar.dto.response.user.userInfoDTO.CreateUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userJoinDTO.CreateUserJoinResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.Mail;
import com.freelance.bookCar.models.booking.Booking;
import com.freelance.bookCar.models.booking.BookingDetail;
import com.freelance.bookCar.models.booking.TypeBooking;
import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.models.product.hotel.HotelBooking;
import com.freelance.bookCar.models.product.ticket.Ticket;
import com.freelance.bookCar.models.product.ticket.Tourism;
import com.freelance.bookCar.models.product.tour.Tour;
import com.freelance.bookCar.models.product.tour.TourSchedule;
import com.freelance.bookCar.models.user.UserInfo;
import com.freelance.bookCar.models.user.UserJoin;
import com.freelance.bookCar.respository.booking.BookingDetailRepository;
import com.freelance.bookCar.respository.booking.BookingRepository;
import com.freelance.bookCar.services.MailService;
import com.freelance.bookCar.services.product.hotelService.hotel.HotelService;
import com.freelance.bookCar.services.product.hotelService.hotelBooking.HotelBookingService;
import com.freelance.bookCar.services.product.ticketService.ticket.TicketService;
import com.freelance.bookCar.services.product.ticketService.tourism.TourismService;
import com.freelance.bookCar.services.product.tourService.tour.TourService;
import com.freelance.bookCar.services.product.tourService.tourSchedule.TourScheduleService;
import com.freelance.bookCar.services.user.userInfoService.UserInfoService;
import com.freelance.bookCar.services.user.userJoinService.UserJoinService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService{
    @Autowired
    private MailService mailService;
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
    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserJoinService userJoinService;

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

                .typeBooking(TypeBooking.CART)
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

    @Override
    public GetBookingResponse findByIdUser(Integer idUser) {
        log.info("Finding booking with idUser: {}", idUser);
        return modelMapper.map(bookingRepository.findByTypeBookingAndIdUser(TypeBooking.CART,idUser), GetBookingResponse.class);
    }

    @Override
    public List<GetBookingDetailResponse> findByIdBooking(Integer idBooking) {
        return bookingDetailRepository.findAllByIdBooking(idBooking).stream().map(bookingDetail
                -> modelMapper.map(bookingDetail, GetBookingDetailResponse.class))
                .collect(Collectors.toList());
    }

    private boolean existBooking(Integer id){
        log.info("Checking if booking exists with id user: {}", id);
        if(bookingRepository.findById(id) != null){
            return true;
        }
        return false;
    }
    private boolean existBookingCart(Integer id){
        log.info("Checking if booking exists with id user: {}", id);
        if(bookingRepository.findByTypeBookingAndIdUser(TypeBooking.CART,id) != null){
            return true;
        }
        return false;
    }

    @Override
    public AddBookingTourResponse addBookingTour(AddBookingTourRequest addBookingTourRequest) {
        log.info("Adding tour to booking: {}", addBookingTourRequest.toString());

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
        if (!existBookingCart(addBookingTourRequest.getIdUser())) {
            // Create a new booking using Builder and ModelMapper
            CreateBookingRequest createBookingRequest = modelMapper
                    .map(addBookingTourRequest,
                            CreateBookingRequest.class);
            createBookingRequest.setTotalPrice(Double.parseDouble(addBookingTourRequest.getTotalPrice()));
            createBookingRequest.setPaymentMethod(1);
            log.info("createBookingRequest: {}", createBookingRequest.toString());
            // Default payment method logic can be added here if needed
            CreateBookingResponse createBookingResponse = create(createBookingRequest);

            // Map the created booking response to the Booking entity using ModelMapper
            booking = modelMapper.map(createBookingResponse, Booking.class);

            log.info("booking1: {}", booking.toString());
        }
        else {
            // Retrieve the existing booking using ModelMapper
            booking = modelMapper.map(findById(addBookingTourRequest.getIdBooking()), Booking.class);

            // Update the total price by adding the price of the new tour
            booking.setDateBook(booking.getDateBook());
            booking.setTotalPrice(booking.getTotalPrice() + Double.parseDouble(addBookingTourRequest.getTotalPrice()));
            booking.setIdUser(booking.getIdUser());
            booking.setTypeBooking(TypeBooking.CART);
            booking.setUserInfo(new ArrayList<>());
            booking.setUserJoin(new ArrayList<>());
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
        BookingDetail bookingDetail=bookingDetaiTourlList(booking.getId(), tour.getId());
        if(bookingDetail==null) {
            // Create a BookingDetail object using Builder pattern
             bookingDetail = BookingDetail.builder()
                    .id(getGenerationId())
                    .idBooking(booking.getId())
                    .idTour(tour.getId())
                    .quantity(addBookingTourRequest.getQuantity())
                    .totalPrice(tour.getPriceTour() * addBookingTourRequest.getQuantity())
                    .build();
        }else{
            bookingDetail.setQuantity(addBookingTourRequest.getQuantity()+bookingDetail.getQuantity());
            bookingDetail.setTotalPrice(booking.getTotalPrice()*bookingDetail.getQuantity());
        }
        log.info("bookingDetail: {}", bookingDetail.toString());

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

        log.info("booking: {}", booking.toString());
        // Map the updated booking to AddBookingTourResponse using ModelMapper
        AddBookingTourResponse response = modelMapper.map(booking, AddBookingTourResponse.class);
        response.setIdTour(addBookingTourRequest.getIdTour());
        response.setQuantity(addBookingTourRequest.getQuantity());
        response.setTotalPrice(booking.getTotalPrice());
        log.info("response: {}", response.toString());
        return response;
    }

    @Override
    public AddBookingTourismResponse addBookingTourism(AddBookingTourismRequest addBookingTourismRequest) {
        log.info("Adding tourism to booking: {}", addBookingTourismRequest.toString());

        log.info("Adding booking tourism request: {}", addBookingTourismRequest.toString());

        if(addBookingTourismRequest.getIdTicket() == null){
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
        Booking booking = new Booking();
        if (!existBookingCart(addBookingTourismRequest.getIdUser())) {
            // Create a new booking using Builder and ModelMapper
            CreateBookingRequest createBookingRequest = modelMapper.map(addBookingTourismRequest, CreateBookingRequest.class);
            createBookingRequest.setTotalPrice(addBookingTourismRequest.getTotalPrice());
            createBookingRequest.setPaymentMethod(1);
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
                    .idPayment(1)
                    .build();
            booking.setTypeBooking(TypeBooking.CART);
            booking.setUserInfo(new ArrayList<>());
            booking.setUserJoin(new ArrayList<>());

            // Save the updated booking
            bookingRepository.save(booking);
        }

        // Get the Tourism entity
        Ticket tourism = modelMapper.map(ticketService.findById(addBookingTourismRequest.getIdTicket()), Ticket.class);
        BookingDetail bookingDetail=bookingDetailTourismList(booking.getId(), tourism.getId());
        if(bookingDetail==null) {
            // Create a BookingDetail object using Builder pattern
            bookingDetail = BookingDetail.builder()
                    .id(getGenerationId())
                    .idBooking(booking.getId())
                    .idTicket(tourism.getId())
                    .quantity(addBookingTourismRequest.getQuantity())
                    .totalPrice(tourism.getTourPrice() * addBookingTourismRequest.getQuantity())
                    .build();
        }else{
            bookingDetail.setQuantity(addBookingTourismRequest.getQuantity()+bookingDetail.getQuantity());
            bookingDetail.setTotalPrice(booking.getTotalPrice()*bookingDetail.getQuantity());
        }
        // Create a BookingDetail for tourism using Builder pattern

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
        response.setIdTourism(addBookingTourismRequest.getIdTicket());
        response.setQuantity(addBookingTourismRequest.getQuantity());
        response.setTotalPrice(booking.getTotalPrice());

        return response;
    }

    @Override
    public AddBookingHotelResponse addBookingHotel(AddBookingHotelRequest addBookingHotelRequest) {
        log.info("Adding booking hotel request: {}", addBookingHotelRequest.toString());

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
        Booking booking = new Booking();
        if (!existBookingCart(addBookingHotelRequest.getIdUser())) {
            // Create a new booking using Builder and ModelMapper
            CreateBookingRequest createBookingRequest = modelMapper.map(addBookingHotelRequest, CreateBookingRequest.class);
            createBookingRequest.setTotalPrice(addBookingHotelRequest.getTotalPrice());
            createBookingRequest.setPaymentMethod(1);


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
                    .id(booking.getId())
                    .dateBook(booking.getDateBook())
                    .totalPrice(booking.getTotalPrice() + addBookingHotelRequest.getTotalPrice())
                    .idUser(booking.getIdUser())
                    .build();

            booking.setTypeBooking(TypeBooking.CART);
            booking.setUserInfo(new ArrayList<>());
            booking.setUserJoin(new ArrayList<>());
            // Save the updated booking
            bookingRepository.save(booking);
        }

        // Get the Hotel entity
        HotelBooking hotel = modelMapper.map(hotelBookingService.findById(addBookingHotelRequest.getIdHotel()), HotelBooking.class);
        //Hotel hotels = modelMapper.map(hotelService.findById(addBookingHotelRequest.getIdHotel()), Hotel.class);

        log.info("429: {}", hotel.getId());
        BookingDetail bookingDetail=bookingDetailHotelList(booking.getId(), hotel.getId());
        log.info("430: {}", bookingDetail!=null ? bookingDetail.toString() : "null");
        if(bookingDetail==null) {
            // Create a BookingDetail object using Builder pattern

            bookingDetail = BookingDetail.builder()
                    .id(getGenerationId())
                    .idBooking(booking.getId())
                    .idHotel(hotel.getId())
                    .quantity(addBookingHotelRequest.getQuantity())
                    .totalPrice(hotel.getTotalPrice() * addBookingHotelRequest.getQuantity())
                    .build();
        }else{
            bookingDetail.setQuantity(addBookingHotelRequest.getQuantity()+bookingDetail.getQuantity());
            bookingDetail.setTotalPrice(booking.getTotalPrice()*bookingDetail.getQuantity());
        }

        try {
            // Save the booking detail for hotel
            booking.setTypeBooking(TypeBooking.CART);
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
        log.info("Updating tourism in booking: {}", updateBookingTourismRequest.toString());

        // Fetch the BookingDetail for the tourism entry
        BookingDetail bookingDetail = bookingDetailRepository.findById(updateBookingTourismRequest.getIdBooking())
                .orElseThrow(() -> new CustomException(Error.BOOKING_DETAIL_NOT_FOUND));

        // Update the quantity and recalculate the total price for the tourism
        if(updateBookingTourismRequest.getQuantity() != null){
            bookingDetail.setQuantity(updateBookingTourismRequest.getQuantity());
        }

        // Fetch the tourism ticket to recalculate the price
        Ticket tourism = modelMapper.map(ticketService.findById(bookingDetail.getIdTicket()), Ticket.class);
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
        response.setIdTourism(bookingDetail.getIdTicket());
        response.setQuantity(bookingDetail.getQuantity());
        response.setTotalPrice(booking.getTotalPrice()); // Updated total price

        return response;
    }
    @Override
    public UpdateBookingHotelResponse updateBookingHotel(UpdateBookingHotelRequest updateBookingHotelRequest) {
        log.info("Updating hotel in booking: {}", updateBookingHotelRequest.toString());

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
            booking.setTypeBooking(TypeBooking.CART);
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
    public List<GetBookingResponse> findType(String type) {
        return bookingRepository.findAllByTypeBooking(TypeBooking.valueOf(type)).stream().map(booking -> modelMapper.map(booking, GetBookingResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetBookingResponse updateType(Integer id,String type) {
        GetBookingResponse getBookingResponse=findById(id);
        Booking booking=modelMapper.map(getBookingResponse,Booking.class);
        booking.setTypeBooking(TypeBooking.valueOf(type));
        Booking bookingsave=bookingRepository.save(booking);
        //Mail mail=mailService.getMail(booking.getUserInfo().getFirst().getEmail(),"Đơn hàng số "+booking.getId()+ "của bạn đã được "+type+"vui long kiểm tra lại ","Đơn hàng số"+booking.getId());
        //mailService.sendMail(mail);
        return modelMapper.map(bookingsave, GetBookingResponse.class);
    }

    @Override
    public OrderResponse order(OrderRequest orderRequest) {
        GetBookingResponse getBookingResponse=findById(orderRequest.getId());
        Booking booking=modelMapper.map(getBookingResponse,Booking.class);
        Booking bookingsave= bookingRepository.save(booking);
        log.info("Booking id 598: {}" , booking.getId());
        List<UserInfo> updatedUserInfo = orderRequest.getCreateUserInfoRequest().stream()
                .map(createUserInfoResponse -> {
                     UserInfo userInfo = modelMapper.map(createUserInfoResponse, UserInfo.class);
                     userInfo.setBooking(bookingsave);
                    UserInfo userInfoSave=modelMapper.map( userInfoService.create(modelMapper.map(userInfo, CreateUserInfoRequest.class)),UserInfo.class);
                     return userInfoSave;
                 })

                .toList();
        log.info("600: {}", updatedUserInfo.toString());



        List<UserJoin> updatedUserJoin = orderRequest.getCreateUserJoinRequest().stream()
                .map(createUserJoinResponse -> {
                    UserJoin userJoin = modelMapper.map(createUserJoinResponse, UserJoin.class);
                    userJoin.setBooking(bookingsave);
                    UserJoin userInfoSave=modelMapper.map( userJoinService.create(modelMapper.map(userJoin, CreateUserJoinRequest.class)),UserJoin.class);
                    return userInfoSave;
                })
                .toList();
        log.info("609: {}", updatedUserJoin.toString());


        log.info("615: {}", bookingsave.toString());

        Mail mail=mailService.getMail(updatedUserInfo.getFirst().getEmail(),"Đơn hàng số "+booking.getId()+ "của bạn đã được đặt vui lòng kiểm tra lại ","Đơn hàng số: "+booking.getId());
        mailService.sendMail(mail);
        return modelMapper.map(bookingsave,OrderResponse.class);
    }

    @Override
    public List<GetBookingResponse> findId(Integer idUser) {
        List<Booking> bookingList=bookingRepository.findAllByIdUser(idUser);
        return bookingList.stream().map(booking -> {
            GetBookingResponse getBookingResponse=modelMapper.map(booking, GetBookingResponse.class);
            getBookingResponse.setType(booking.getTypeBooking().name());
            return getBookingResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteBookingDetail(Integer id) {
        BookingDetail bookingDetail=bookingDetailRepository.findById(id).orElseThrow();
        bookingDetailRepository.delete(bookingDetail);
    }

    @Override
    public List<GetBookingResponse> findAllByTypeBookingNotAndIdUser(Integer idUser) {
        return bookingRepository.findAllByTypeBookingNotAndIdUser(TypeBooking.CART, idUser).stream()
                .map(booking -> modelMapper.map(booking, GetBookingResponse.class)) // Chuyển đổi Booking thành GetBookingResponse
                .collect(Collectors.toList());
    }


    @Override
    public UpdateBookingTourResponse updateBookingTour(UpdateBookingTourRequest updateBookingTourRequest) {
        log.info("Updating tour in booking: {}", updateBookingTourRequest.toString());

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

    private BookingDetail bookingDetailHotelList(Integer idBooking, Integer idHotel){
        return bookingDetailRepository.findByIdBookingAndIdHotel(idBooking, idHotel);
    }
    private BookingDetail bookingDetaiTourlList(Integer idBooking, Integer idTour){
        return bookingDetailRepository.findByIdBookingAndIdTour(idBooking, idTour);
    }
    private BookingDetail bookingDetailTourismList(Integer idBooking, Integer idTour){
        return bookingDetailRepository.findByIdBookingAndIdTicket(idBooking, idTour);
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
