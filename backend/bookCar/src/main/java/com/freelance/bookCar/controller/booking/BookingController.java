package com.freelance.bookCar.controller.booking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelance.bookCar.dto.ApiResponse;
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
import com.freelance.bookCar.dto.response.user.userInfoDTO.GetUserInfoResponse;
import com.freelance.bookCar.dto.response.user.userJoinDTO.GetUserJoinResponse;
import com.freelance.bookCar.respository.user.UserInfoRepository;
import com.freelance.bookCar.services.booking.BookingService;
import com.freelance.bookCar.services.user.userInfoService.UserInfoService;
import com.freelance.bookCar.services.user.userJoinService.UserJoinService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin(origins = "*")
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserJoinService userJoinService;
    @Autowired
    private UserInfoService userInfoService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateBookingResponse>> create(@RequestBody CreateBookingRequest createBookingRequest){
        CreateBookingResponse createBookingResponse=bookingService.create(createBookingRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", createBookingResponse));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping()
    public ResponseEntity<ApiResponse<UpdateBookingResponse>> update(@RequestBody UpdateBookingRequest updateBookingRequest){
        UpdateBookingResponse updateBookingResponse =  bookingService.update(updateBookingRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking updated successfully", updateBookingResponse));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping()
    public ResponseEntity<ApiResponse<GetBookingResponse>> getById(@RequestParam Integer id){
        GetBookingResponse GetBookingResponse = bookingService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking retrieved successfully", GetBookingResponse));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<GetBookingResponse>> getByIdUser(@RequestParam Integer idUser){
        GetBookingResponse GetBookingResponse = bookingService.findByIdUser(idUser);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking retrieved successfully", GetBookingResponse));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<List<GetBookingDetailResponse>>> getByIdBooking(@RequestParam Integer idBooking){
        List<GetBookingDetailResponse> GetBookingResponse = bookingService.findByIdBooking(idBooking);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking retrieved successfully", GetBookingResponse));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tour")
    public ResponseEntity<ApiResponse<AddBookingTourResponse>> createBookingTour(@ModelAttribute @Valid AddBookingTourRequest addBookingTourRequest){
        AddBookingTourResponse addBookingTourResponse=bookingService.addBookingTour(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/tourism")
    public ResponseEntity<ApiResponse<AddBookingTourismResponse>> createBookingTourism(@ModelAttribute @Valid AddBookingTourismRequest addBookingTourRequest){
        AddBookingTourismResponse addBookingTourResponse=bookingService.addBookingTourism(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/hotel")
    public ResponseEntity<ApiResponse<AddBookingHotelResponse>> createBookingHotel(@ModelAttribute @Valid AddBookingHotelRequest addBookingTourRequest){
        AddBookingHotelResponse addBookingTourResponse=bookingService.addBookingHotel(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<GetBookingResponse>>> getAll(){
        List<GetBookingResponse> getBookingResponses=bookingService.getAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", getBookingResponses));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<GetBookingResponse>>> findAllByTypeBookingNotAndIdUser(@RequestParam Integer idUser){
        List<GetBookingResponse> getBookingResponses=bookingService.findAllByTypeBookingNotAndIdUser(idUser);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", getBookingResponses));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/type")
    public ResponseEntity<ApiResponse<List<GetBookingResponse>>> getType(@RequestParam String type){
        List<GetBookingResponse> getBookingResponses=bookingService.findType(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", getBookingResponses));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping("/settype")
    public ResponseEntity<ApiResponse<GetBookingResponse>> setType(@RequestParam String type,@RequestParam Integer id){
        GetBookingResponse getBookingResponses=bookingService.updateType(id, type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", getBookingResponses));
    }
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/tour")
    public ResponseEntity<ApiResponse<UpdateBookingTourResponse>> updateBookingTour(@ModelAttribute @Valid UpdateBookingTourRequest updateBookingTourRequest){
        UpdateBookingTourResponse addBookingTourResponse=bookingService.updateBookingTour(updateBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/tourism")
    public ResponseEntity<ApiResponse<UpdateBookingTourismResponse>> updateBookingTourism(@ModelAttribute @Valid UpdateBookingTourismRequest addBookingTourRequest){
        UpdateBookingTourismResponse addBookingTourResponse=bookingService.updateBookingTourism(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PatchMapping("/hotel")
    public ResponseEntity<ApiResponse<UpdateBookingHotelResponse>> createBookingHotel(@ModelAttribute @Valid UpdateBookingHotelRequest addBookingTourRequest){
        UpdateBookingHotelResponse addBookingTourResponse=bookingService.updateBookingHotel(addBookingTourRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
    }


    @PatchMapping(value = "/order", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<OrderResponse>> order(@RequestParam("id") Integer id,
                                                            @RequestParam("idUser") Integer idUser,
                                                            @RequestParam("dateBook") String dateBook,
                                                            @RequestParam("totalPrice") String totalPrice,
                                                            @RequestParam("createUserInfoRequest") String createUserInfoRequestJson,
                                                            @RequestParam("createUserJoinRequest") String createUserJoinRequestJson,
                                                            @RequestParam("paymentMethod") Integer paymentMethod,
                                                            @RequestParam("idVoucher") Integer idVoucher){
        try{
            ObjectMapper objectMapper = new ObjectMapper();

            List<CreateUserInfoRequest> createUserInfoRequest = objectMapper.readValue(
                    createUserInfoRequestJson, new TypeReference<List<CreateUserInfoRequest>>() {});
            List<CreateUserJoinRequest> createUserJoinRequest = objectMapper.readValue(
                    createUserJoinRequestJson, new TypeReference<List<CreateUserJoinRequest>>() {});

            OrderRequest orderRequest = new OrderRequest(
                    id,
                    LocalDateTime.parse(dateBook),
                    totalPrice,
                    idUser,
                    createUserInfoRequest,
                    createUserJoinRequest,
                    paymentMethod,
                    idVoucher
            );
            log.info("Order request: {}", orderRequest.toString());
            OrderResponse addBookingTourResponse=bookingService.order(orderRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", addBookingTourResponse));
        } catch (Exception e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Error processing request", null));
        }

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/id")
    public ResponseEntity<ApiResponse<List<GetBookingResponse>>> getId(@RequestParam Integer type){
        List<GetBookingResponse> getBookingResponses=bookingService.findId(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", getBookingResponses));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping()
    public ResponseEntity<ApiResponse<?>> deleteId(@RequestParam Integer id){
      bookingService.deleteBookingDetail(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", "ok"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/userinfo")
    public ResponseEntity<ApiResponse<List<GetUserInfoResponse>>> getUserInfo(@RequestParam Integer idBooking){
        List<GetUserInfoResponse> createUserInfoRequest = userInfoService.getUserInfoByBookingId(idBooking);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", createUserInfoRequest));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/userjoin")
    public ResponseEntity<ApiResponse<List<GetUserJoinResponse>>> getUserJoin(@RequestParam Integer idBooking){
        List<GetUserJoinResponse> createUserInfoRequest = userJoinService.getUserJoinByBookingId(idBooking);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking created successfully", createUserInfoRequest));
    }


}
