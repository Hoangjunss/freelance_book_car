package com.freelance.bookCar.models.booking;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TypeBooking {

    PENDING,ACCEPT,CANCEL,SUCCESS,CART
}
