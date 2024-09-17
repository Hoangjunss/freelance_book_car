package com.freelance.bookCar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum Error {
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND), //Resource not found
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST), //Syntax error or malformed request
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED), // unauthenticated user
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN), //The user does not have permission to access the resource
    CONFLICT(409, "Conflict", HttpStatus.CONFLICT), // Resource state conflicts. For example, it can happen when trying to create a duplicate record or update data that is being edited at the same time by someone else.
    //Server Error
    UNCATEGORIZED_EXCEPTION(9999, "Unclassified error", HttpStatus.INTERNAL_SERVER_ERROR),
    //Database Error
    DATABASE_ACCESS_ERROR(9998, "Database access error", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY(9996, "Duplicate key found", HttpStatus.CONFLICT),
    EMPTY_RESULT(9995, "No result found", HttpStatus.NOT_FOUND),
    NON_UNIQUE_RESULT(9994, "Non-unique result found", HttpStatus.CONFLICT),
    //Account Error
    ACCOUNT_NOT_FOUND(1001, "Account not found", HttpStatus.NOT_FOUND),
    ACCOUNT_ALREADY_EXISTS(1002, "Account already exists", HttpStatus.CONFLICT),
    ACCOUNT_UNABLE_TO_SAVE(1003, "Unable to save account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_UNABLE_TO_UPDATE(1004, "Unable to update account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_UNABLE_TO_DELETE(1005, "Unable to delete account", HttpStatus.INTERNAL_SERVER_ERROR),
    //User error
    USER_NOT_FOUND(1101, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1102, "User already exists", HttpStatus.CONFLICT),
    USER_UNABLE_TO_SAVE(1103, "Unable to save user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNABLE_TO_UPDATE(1104, "Unable to update user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNABLE_TO_DELETE(1105, "Unable to delete user", HttpStatus.INTERNAL_SERVER_ERROR),
    //Ticket Error
    TICKET_NOT_FOUND(1201, "Ticket not found", HttpStatus.NOT_FOUND),
    TICKET_ALREADY_EXISTS(1202, "Ticket already exists", HttpStatus.CONFLICT),
    TICKET_UNABLE_TO_SAVE(1203, "Unable to save ticket", HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_UNABLE_TO_UPDATE(1204, "Unable to update ticket", HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_UNABLE_TO_DELETE(1205, "Unable to delete ticket", HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_INVALID_START_DATE(1206, "Invalid start", HttpStatus.BAD_REQUEST),
    TICKET_INVALID_TOUR_PRICE(1207, "Invalid price", HttpStatus.BAD_REQUEST),
    //Tour error
    TOUR_NOT_FOUND(1301, "Tour not found", HttpStatus.NOT_FOUND),
    TOUR_ALREADY_EXISTS(1302, "Tour already exists", HttpStatus.CONFLICT),
    TOUR_UNABLE_TO_SAVE(1303, "Unable to save tour", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_UNABLE_TO_UPDATE(1304, "Unable to update tour", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_UNABLE_TO_DELETE(1305, "Unable to delete tour", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_INVALID_NAME(1306, "Tour name is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_DESCRIPTION(1307, "Tour description is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_START_LOCATION(1308, "Tour start location is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_END_LOCATION(1309, "Tour end location is missing or invalid", HttpStatus.BAD_REQUEST),
    //TourSchedule Error
    TOUR_SCHEDULE_NOT_FOUND(1401, "Tour schedule not found", HttpStatus.NOT_FOUND),
    TOUR_SCHEDULE_ALREADY_EXISTS(1402, "Tour schedule already exists", HttpStatus.CONFLICT),
    TOUR_SCHEDULE_UNABLE_TO_SAVE(1403, "Unable to save tour schedule", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_SCHEDULE_UNABLE_TO_UPDATE(1404, "Unable to update tour schedule", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_SCHEDULE_UNABLE_TO_DELETE(1405, "Unable to delete tour schedule", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_SCHEDULE_INVALID_START_TIME(1406, "Tour start time is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_SCHEDULE_INVALID_END_TIME(1407, "Tour end time is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_SCHEDULE_MISSING_TOUR_ID(1408, "Tour ID is missing", HttpStatus.BAD_REQUEST),
    TOUR_SCHEDULE_INVALID_QUANTITY(1409, "Tour quantity is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_SCHEDULE_INVALID_PRICE(1410, "Tour price is missing or invalid", HttpStatus.BAD_REQUEST),
    //Tourism error
    TOURISM_NOT_FOUND(1501, "Tourism not found", HttpStatus.NOT_FOUND),
    TOURISM_ALREADY_EXISTS(1502, "Tourism already exists", HttpStatus.CONFLICT),
    TOURISM_UNABLE_TO_SAVE(1503, "Unable to save tourism", HttpStatus.INTERNAL_SERVER_ERROR),
    TOURISM_UNABLE_TO_UPDATE(1504, "Unable to update tourism", HttpStatus.INTERNAL_SERVER_ERROR),
    TOURISM_UNABLE_TO_DELETE(1505, "Unable to delete tourism", HttpStatus.INTERNAL_SERVER_ERROR),
    TOURISM_INVALID_NAME(1506, "Invalid name", HttpStatus.BAD_REQUEST),
    TOURISM_INVALID_LOCATION(1506, "Invalid location", HttpStatus.BAD_REQUEST),
    TOURISM_INVALID_DESCRIPTION(1507, "Invalid description", HttpStatus.BAD_REQUEST),
    TOURISM_INVALID_RATING(1508, "Invalid rating", HttpStatus.BAD_REQUEST),
    //Hotel error
    HOTEL_NOT_FOUND(1601, "Hotel not found", HttpStatus.NOT_FOUND),
    HOTEL_ALREADY_EXISTS(1602, "Hotel already exists", HttpStatus.CONFLICT),
    HOTEL_UNABLE_TO_SAVE(1603, "Unable to save hotel", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_UNABLE_TO_UPDATE(1604, "Unable to update hotel", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_UNABLE_TO_DELETE(1605, "Unable to delete hotel", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_INVALID_NAME(1606, "Invalid name", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_LOCATION(1607, "Invalid location", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_CONTACT_INFO(1608, "Invalid contact info", HttpStatus.BAD_REQUEST),
    //Hotel Booking error
    HOTEL_BOOKING_NOT_FOUND(1701, "Hotel booking not found", HttpStatus.NOT_FOUND),
    HOTEL_BOOKING_ALREADY_EXISTS(1702, "Hotel booking already exists", HttpStatus.CONFLICT),
    HOTEL_BOOKING_UNABLE_TO_SAVE(1703, "Unable to save hotel booking", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_BOOKING_UNABLE_TO_UPDATE(1704, "Unable to update hotel booking", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_BOOKING_UNABLE_TO_DELETE(1705, "Unable to delete hotel booking", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_BOOKING_INVALID_START_DATE(1706, "Invalid start date", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_END_DATE(1707, "Invalid end date", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_NUMBER_OF_DAYS(1708, "Invalid number of days", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_CHECK_IN_TIME(1709, "Invalid check-in time", HttpStatus.BAD_REQUEST),
    //Invoice error
    INVOICE_NOT_FOUND(1801, "Invoice not found", HttpStatus.NOT_FOUND),
    INVOICE_ALREADY_EXISTS(1802, "Invoice already exists", HttpStatus.CONFLICT),
    INVOICE_UNABLE_TO_SAVE(1803, "Unable to save invoice", HttpStatus.INTERNAL_SERVER_ERROR),
    INVOICE_UNABLE_TO_UPDATE(1804, "Unable to update invoice", HttpStatus.INTERNAL_SERVER_ERROR),
    INVOICE_UNABLE_TO_DELETE(1805, "Unable to delete invoice", HttpStatus.INTERNAL_SERVER_ERROR),
    INVOICE_INVALID_TOTAL_AMOUNT(1806, "Invalid total amount", HttpStatus.BAD_REQUEST),
    INVOICE_INVALID_PAYMENT_STATUS(1807, "Invalid payment status", HttpStatus.BAD_REQUEST),
    INVOICE_INVALID_ID_BOOKING(1808, "Invalid id booking", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    /**
     * Constructor for ErrorCode.
     *
     * @param code       the error code
     * @param message    the error message
     * @param statusCode the corresponding HTTP status code
     */
    Error(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
