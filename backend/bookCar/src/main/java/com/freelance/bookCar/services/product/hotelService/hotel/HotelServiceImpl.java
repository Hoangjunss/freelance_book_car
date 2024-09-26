package com.freelance.bookCar.services.product.hotelService.hotel;

import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.CreateHotelRequest;
import com.freelance.bookCar.dto.request.product.hotelDTO.hotel.UpdateHotelRequest;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.CreateHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.GetHotelResponse;
import com.freelance.bookCar.dto.response.product.hotelDTO.hotel.UpdateHotelResponse;
import com.freelance.bookCar.exception.CustomException;
import com.freelance.bookCar.exception.Error;
import com.freelance.bookCar.models.product.hotel.Hotel;
import com.freelance.bookCar.respository.product.hotel.HotelRepository;
import com.freelance.bookCar.services.CloudinaryService;
import com.freelance.bookCar.services.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ImageService imageService;
    @Override
    public CreateHotelResponse createHotel(CreateHotelRequest createHotelRequest) {
        log.info("Creating hotel with name: {}", createHotelRequest.getName());

        // Validation
        if (createHotelRequest.getName() == null) {
            throw new CustomException(Error.HOTEL_INVALID_NAME);
        }
        if(createHotelRequest.getContactInfo() == null){
            throw new CustomException(Error.HOTEL_INVALID_CONTACT_INFO);
        }
        if(createHotelRequest.getPricePerNight() < 0D){
            throw new CustomException(Error.HOTEL_INVALID_PRICE_PER_NIGHT);
        }
        if (createHotelRequest.getLocation() == null) {
            throw new CustomException(Error.HOTEL_INVALID_LOCATION);
        }
        if(createHotelRequest.getRating() < 0D) {
            throw new CustomException(Error.HOTEL_INVALID_RATING);
        }

        Hotel hotel = Hotel.builder()
                .id(getGenerationId())
                .name(createHotelRequest.getName())
                .location(createHotelRequest.getLocation())
                .contactInfo(createHotelRequest.getContactInfo())
                .pricePerNight(createHotelRequest.getPricePerNight())
                .isActive(createHotelRequest.isActive())
                .rating(createHotelRequest.getRating())
                .image(imageService.saveImage(createHotelRequest.getImage()))
                .build();

        try {
            return modelMapper.map(hotelRepository.save(hotel), CreateHotelResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while saving hotel: {}", e.getMessage(), e);
            throw new CustomException(Error.HOTEL_UNABLE_TO_SAVE);
        }
    }

    @Override
    public UpdateHotelResponse updateHotel(UpdateHotelRequest updateHotelRequest) {
        log.info("Updating hotel with id: {}", updateHotelRequest.getId());

        if (updateHotelRequest.getId() == null) {
            throw new CustomException(Error.HOTEL_NOT_FOUND);
        }

        Hotel existingHotel = hotelRepository.findById(updateHotelRequest.getId())
                .orElseThrow(() -> new CustomException(Error.HOTEL_NOT_FOUND));

        if (updateHotelRequest.getContactInfo() != null) {
            existingHotel.setContactInfo(updateHotelRequest.getContactInfo());
        }
        if (updateHotelRequest.getLocation() != null) {
            existingHotel.setLocation(updateHotelRequest.getLocation());
        }
        if (updateHotelRequest.getName() != null) {
            existingHotel.setName(updateHotelRequest.getName());
        }
        if (updateHotelRequest.getPricePerNight() >= 0D) {
            existingHotel.setPricePerNight(updateHotelRequest.getPricePerNight());
        }
            existingHotel.setActive(updateHotelRequest.isActive());

        if(updateHotelRequest.getRating() >= 0D) {
            existingHotel.setRating(updateHotelRequest.getRating());
        }
        if (updateHotelRequest.getImage()!=null){
            existingHotel.setImage(imageService.saveImage(updateHotelRequest.getImage()));
        }

        try {
            return modelMapper.map(hotelRepository.save(existingHotel), UpdateHotelResponse.class);
        } catch (Exception e) {
            log.error("Error occurred while updating hotel: {}", e.getMessage(), e);
            throw new CustomException(Error.HOTEL_UNABLE_TO_UPDATE);
        }
    }

    @Override
    public GetHotelResponse findById(Integer id) {
        log.info("Finding hotel with id: {}", id);

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new CustomException(Error.HOTEL_NOT_FOUND));

        return modelMapper.map(hotel, GetHotelResponse.class);
    }

    @Override
    public List<GetHotelResponse> getAll() {
        return hotelRepository.findAll().stream().map(hotel -> modelMapper.map(hotel, GetHotelResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<GetHotelResponse> findByLocation(String location) {
        return hotelRepository.findAllByLocation(location).stream().map(hotel -> modelMapper.map(hotel, GetHotelResponse.class)).collect(Collectors.toList());
    }

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
}
