package com.freelance.bookCar.dto.response.booking;

import com.freelance.bookCar.models.product.voucher.Voucher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBookingResponse {
    private Integer id;
    private LocalDateTime dateBook;
    private double totalPrice;
    private Integer idUser;
    private Integer paymentMethod;
    private Integer invoice;
    private Voucher voucher;
    private String type;
    private Integer idTour;
}
