package com.freelance.bookCar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum Error {
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND), // Resource not found
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST), // Syntax error or malformed request
    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED), // unauthenticated user
    FORBIDDEN(403, "Forbidden", HttpStatus.FORBIDDEN), // The user does not have permission to access the resource
    CONFLICT(409, "Conflict", HttpStatus.CONFLICT), // Resource state conflicts. For example, it can happen when trying
                                                    // to create a duplicate record or update data that is being edited
                                                    // at the same time by someone else.
    // Server Error
    UNCATEGORIZED_EXCEPTION(9999, "Unclassified error", HttpStatus.INTERNAL_SERVER_ERROR),
    // Database Error
    DATABASE_ACCESS_ERROR(9998, "Database access error", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_KEY(9996, "Duplicate key found", HttpStatus.CONFLICT),
    EMPTY_RESULT(9995, "No result found", HttpStatus.NOT_FOUND),
    NON_UNIQUE_RESULT(9994, "Non-unique result found", HttpStatus.CONFLICT),
    // Account Error
    ACCOUNT_NOT_FOUND(1001, "Account not found", HttpStatus.NOT_FOUND),
    ACCOUNT_ALREADY_EXISTS(1002, "Account already exists", HttpStatus.CONFLICT),
    ACCOUNT_UNABLE_TO_SAVE(1003, "Unable to save account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_UNABLE_TO_UPDATE(1004, "Unable to update account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_UNABLE_TO_DELETE(1005, "Unable to delete account", HttpStatus.INTERNAL_SERVER_ERROR),
    ACCOUNT_PASSWORD_NOT_MACHINES(1006, "Password not machines", HttpStatus.INTERNAL_SERVER_ERROR),
    // User error
    USER_NOT_FOUND(1101, "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1102, "User already exists", HttpStatus.CONFLICT),
    USER_UNABLE_TO_SAVE(1103, "Unable to save user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNABLE_TO_UPDATE(1104, "Unable to update user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNABLE_TO_DELETE(1105, "Unable to delete user", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_INVALID_EMAIL(1106, "Invalid email address", HttpStatus.BAD_REQUEST),
    USER_INVALID_PHONE(1109, "Invalid phone number", HttpStatus.BAD_REQUEST),
    USER_INVALID_ADDRESS(1110, "Invalid address", HttpStatus.BAD_REQUEST),
    USER_INVALID_PASSWORD_FORMAT(1112, "Invalid password format", HttpStatus.BAD_REQUEST),
    USER_INVALID_PASSWORD(1107, "Invalid password", HttpStatus.BAD_REQUEST),
    USER_INVALID_NAME(1108, "Invalid Name", HttpStatus.BAD_REQUEST),
    // Ticket Error
    TICKET_NOT_FOUND(1201, "Ticket not found", HttpStatus.NOT_FOUND),
    TICKET_ALREADY_EXISTS(1202, "Ticket already exists", HttpStatus.CONFLICT),
    TICKET_UNABLE_TO_SAVE(1203, "Unable to save ticket", HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_UNABLE_TO_UPDATE(1204, "Unable to update ticket", HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_UNABLE_TO_DELETE(1205, "Unable to delete ticket", HttpStatus.INTERNAL_SERVER_ERROR),
    TICKET_INVALID_START_DATE(1206, "Invalid start", HttpStatus.BAD_REQUEST),
    TICKET_INVALID_TOUR_PRICE(1207, "Invalid price", HttpStatus.BAD_REQUEST),
    TICKET_INVALID_ID_TOURISM(1208, "Invalid id Tourism", HttpStatus.BAD_REQUEST),
    // Tour error
    TOUR_NOT_FOUND(1301, "Tour not found", HttpStatus.NOT_FOUND),
    TOUR_ALREADY_EXISTS(1302, "Tour already exists", HttpStatus.CONFLICT),
    TOUR_UNABLE_TO_SAVE(1303, "Unable to save tour", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_UNABLE_TO_UPDATE(1304, "Unable to update tour", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_UNABLE_TO_DELETE(1305, "Unable to delete tour", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_INVALID_NAME(1306, "Tour name is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_DESCRIPTION(1307, "Tour description is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_START_LOCATION(1308, "Tour start location is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_END_LOCATION(1309, "Tour end location is missing or invalid", HttpStatus.BAD_REQUEST),
    TOUR_INVALID_ID_TOUR_STATUS(1411, "Tour status is invalid or invalid", HttpStatus.BAD_REQUEST),
    //Tour status
    TOUR_STATUS_NOT_FOUND(1412, "Tour status not found", HttpStatus.NOT_FOUND),
    TOUR_STATUS_ALREADY_EXISTS(1413, "Tour status already exists", HttpStatus.CONFLICT),
    TOUR_STATUS_UNABLE_TO_SAVE(1414, "Unable to save tour status", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_STATUS_UNABLE_TO_UPDATE(1415, "Unable to update tour status", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_STATUS_UNABLE_TO_DELETE(1416, "Unable to delete tour status", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_STATUS_INVALID_NAME(1417, "Invalid name", HttpStatus.BAD_REQUEST),
    // TourSchedule Error
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
    TOUR_SCHEDULE_INVALID_STATUS(1411, "Tour status is invalid or invalid", HttpStatus.BAD_REQUEST),
    //Tour schedule status
    TOUR_SCHEDULE_STATUS_NOT_FOUND(1412, "Tour schedule status not found", HttpStatus.NOT_FOUND),
    TOUR_SCHEDULE_STATUS_ALREADY_EXISTS(1413, "Tour schedule status already exists", HttpStatus.CONFLICT),
    TOUR_SCHEDULE_STATUS_UNABLE_TO_SAVE(1414, "Unable to save tour schedule status", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_SCHEDULE_STATUS_UNABLE_TO_UPDATE(1415, "Unable to update tour schedule status", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_SCHEDULE_STATUS_UNABLE_TO_DELETE(1416, "Unable to delete tour schedule status", HttpStatus.INTERNAL_SERVER_ERROR),
    TOUR_SCHEDULE_STATUS_INVALID_NAME(1417, "Invalid name", HttpStatus.BAD_REQUEST),
    // Tourism error
    TOURISM_NOT_FOUND(1501, "Tourism not found", HttpStatus.NOT_FOUND),
    TOURISM_ALREADY_EXISTS(1502, "Tourism already exists", HttpStatus.CONFLICT),
    TOURISM_UNABLE_TO_SAVE(1503, "Unable to save tourism", HttpStatus.INTERNAL_SERVER_ERROR),
    TOURISM_UNABLE_TO_UPDATE(1504, "Unable to update tourism", HttpStatus.INTERNAL_SERVER_ERROR),
    TOURISM_UNABLE_TO_DELETE(1505, "Unable to delete tourism", HttpStatus.INTERNAL_SERVER_ERROR),
    TOURISM_INVALID_NAME(1506, "Invalid name", HttpStatus.BAD_REQUEST),
    TOURISM_INVALID_LOCATION(1506, "Invalid location", HttpStatus.BAD_REQUEST),
    TOURISM_INVALID_DESCRIPTION(1507, "Invalid description", HttpStatus.BAD_REQUEST),
    TOURISM_INVALID_RATING(1508, "Invalid rating", HttpStatus.BAD_REQUEST),
    // Hotel error
    HOTEL_NOT_FOUND(1601, "Hotel not found", HttpStatus.NOT_FOUND),
    HOTEL_ALREADY_EXISTS(1602, "Hotel already exists", HttpStatus.CONFLICT),
    HOTEL_UNABLE_TO_SAVE(1603, "Unable to save hotel", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_UNABLE_TO_UPDATE(1604, "Unable to update hotel", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_UNABLE_TO_DELETE(1605, "Unable to delete hotel", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_INVALID_NAME(1606, "Invalid name", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_LOCATION(1607, "Invalid location", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_CONTACT_INFO(1608, "Invalid contact info", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_PRICE_PER_NIGHT(1609, "Invalid Price per night", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_STATUS(1610, "Invalid status", HttpStatus.BAD_REQUEST),
    HOTEL_INVALID_RATING(1611, "Invalid rating", HttpStatus.BAD_REQUEST),
    // Hotel Booking error
    HOTEL_BOOKING_NOT_FOUND(1701, "Hotel booking not found", HttpStatus.NOT_FOUND),
    HOTEL_BOOKING_ALREADY_EXISTS(1702, "Hotel booking already exists", HttpStatus.CONFLICT),
    HOTEL_BOOKING_UNABLE_TO_SAVE(1703, "Unable to save hotel booking", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_BOOKING_UNABLE_TO_UPDATE(1704, "Unable to update hotel booking", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_BOOKING_UNABLE_TO_DELETE(1705, "Unable to delete hotel booking", HttpStatus.INTERNAL_SERVER_ERROR),
    HOTEL_BOOKING_INVALID_START_DATE(1706, "Invalid start date", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_END_DATE(1707, "Invalid end date", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_NUMBER_OF_DAYS(1708, "Invalid number of days", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_CHECK_IN_TIME(1709, "Invalid check-in time", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_ID_HOTEL(1710, "Invalid id-holder", HttpStatus.BAD_REQUEST),
    HOTEL_BOOKING_INVALID_TOTAL_PRICE(1711, "Invalid total price", HttpStatus.BAD_REQUEST),
    // Invoice error
    INVOICE_NOT_FOUND(1801, "Invoice not found", HttpStatus.NOT_FOUND),
    INVOICE_ALREADY_EXISTS(1802, "Invoice already exists", HttpStatus.CONFLICT),
    INVOICE_UNABLE_TO_SAVE(1803, "Unable to save invoice", HttpStatus.INTERNAL_SERVER_ERROR),
    INVOICE_UNABLE_TO_UPDATE(1804, "Unable to update invoice", HttpStatus.INTERNAL_SERVER_ERROR),
    INVOICE_UNABLE_TO_DELETE(1805, "Unable to delete invoice", HttpStatus.INTERNAL_SERVER_ERROR),
    INVOICE_INVALID_ID(1808, "Invalid invoice id", HttpStatus.BAD_REQUEST),
    INVOICE_INVALID_TOTAL_AMOUNT(1806, "Invalid total amount", HttpStatus.BAD_REQUEST),
    INVOICE_INVALID_PAYMENT_STATUS(1807, "Invalid payment status", HttpStatus.BAD_REQUEST),
    INVOICE_INVALID_ID_BOOKING(1808, "Invalid id booking", HttpStatus.BAD_REQUEST),
    // Booking Error
    BOOKING_NOT_FOUND(1901, "Booking not found", HttpStatus.NOT_FOUND),
    BOOKING_ALREADY_EXISTS(1902, "Booking already exists", HttpStatus.CONFLICT),
    BOOKING_UNABLE_TO_SAVE(1903, "Unable to save booking", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_UNABLE_TO_UPDATE(1904, "Unable to update booking", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_UNABLE_TO_DELETE(1905, "Unable to delete booking", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_INVALID_ID_USER(1906, "Invalid user", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID_PAYMENT_METHOD(1907, "Invalid payment method", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID_INVOICE(1908, "Invalid in voice", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID_TOTAL_PRICE(1909, "Invalid total price", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID_DATE_BOOK(1910, "Invalid date book", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID_ID_TOUR(1911, "Invalid id Tour", HttpStatus.BAD_REQUEST),
    BOOKING_INVALID_ID_PAYMENT(1912, "Invalid id payment", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_ID_SERVICE(1913, "Invalid id service", HttpStatus.BAD_REQUEST),
    //Voucher error
    VOUCHER_NOT_FOUND(2001, "Voucher not found", HttpStatus.NOT_FOUND),
    VOUCHER_ALREADY_EXISTS(2002, "Voucher already exists", HttpStatus.CONFLICT),
    VOUCHER_UNABLE_TO_SAVE(2003, "Unable to save voucher", HttpStatus.INTERNAL_SERVER_ERROR),
    VOUCHER_UNABLE_TO_UPDATE(2004, "Unable to update voucher", HttpStatus.INTERNAL_SERVER_ERROR),
    VOUCHER_UNABLE_TO_DELETE(2005, "Unable to delete voucher", HttpStatus.INTERNAL_SERVER_ERROR),
    VOUCHER_INVALID_PROMOTION_ID(2006, "Invalid voucher Promotion id", HttpStatus.BAD_REQUEST),
    VOUCHER_INVALID_CREATE_DATE(2007, "Invalid voucher create date", HttpStatus.BAD_REQUEST),
    //Promotion error
    PROMOTION_NOT_FOUND(2101, "Promotion not found", HttpStatus.NOT_FOUND),
    PROMOTION_ALREADY_EXISTS(2102, "Promotion already exists", HttpStatus.CONFLICT),
    PROMOTION_UNABLE_TO_SAVE(2103, "Unable to save promotion", HttpStatus.INTERNAL_SERVER_ERROR),
    PROMOTION_UNABLE_TO_UPDATE(2104, "Unable to update promotion", HttpStatus.INTERNAL_SERVER_ERROR),
    PROMOTION_UNABLE_TO_DELETE(2105, "Unable to delete promotion", HttpStatus.INTERNAL_SERVER_ERROR),
    PROMOTION_INVALID_NAME(2106, "Invalid name", HttpStatus.BAD_REQUEST),
    PROMOTION_INVALID_DISCOUNT_RATE(2107, "Invalid Discount Rate", HttpStatus.BAD_REQUEST),
    PROMOTION_INVALID_DATE_RANGE(2108, "Invalid Date Range", HttpStatus.BAD_REQUEST),
    //Image error
    IMAGE_NOT_FOUND(2201, "Image not found", HttpStatus.NOT_FOUND),
    IMAGE_ALREADY_EXISTS(2202, "Image already exists", HttpStatus.CONFLICT),
    IMAGE_UNABLE_TO_SAVE(2203, "Unable to save image", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_UNABLE_TO_UPDATE(2204, "Unable to update image", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_UNABLE_TO_DELETE(2205, "Unable to delete image", HttpStatus.INTERNAL_SERVER_ERROR),
    //Booking Details error
    BOOKING_DETAIL_NOT_FOUND(2301, "Booking detail not found", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_ALREADY_EXISTS(2302, "Booking detail already exists", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_UNABLE_TO_SAVE(2303, "Unable to save booking detail", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_DETAIL_UNABLE_TO_UPDATE(2304, "Unable to update booking detail", HttpStatus.INTERNAL_SERVER_ERROR),
    BOOKING_DETAIL_UNABLE_TO_DELETE(2305, "Unable to delete booking detail", HttpStatus.INTERNAL_SERVER_ERROR),
    // Booking Tor
    BOOKING_DETAIL_INVALID_ID(2306, "Booking detail invalid id", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_ID_TOUR(2306, "Booking detail invalid id Tour", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_ID_TOURISM(2306, "Booking detail invalid id Tourism", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_ID_HOTEL(2306, "Booking detail invalid id Hotel", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_QUANTITY(2307, "Booking detail invalid quantity", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_TOTAL_PRICE(2308, "Booking detail invalid total price", HttpStatus.BAD_REQUEST),
    BOOKING_DETAIL_INVALID_ID_USER(2309, "Booking detail invalid Id User", HttpStatus.BAD_REQUEST),
    // Cloudinary-related errors
    UPLOAD_FAILED(2401, "Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FAILED(2402, "Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR),
    CONVERSION_FAILED(2403, "Failed to convert file", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_DELETION_FAILED(2404, "Failed to delete temporary file", HttpStatus.INTERNAL_SERVER_ERROR),
    //Mail error
    MAIL_SENDING_FAILED(2505, "Failed to send mail", HttpStatus.INTERNAL_SERVER_ERROR),
    MAIL_INVALID_DETAILS(2506, "Invalid mail details", HttpStatus.BAD_REQUEST),
    MAIL_INVALID_MAILTO(2507, "Invalid mail to", HttpStatus.BAD_REQUEST),
    MAIL_INVALID_CONTENT(2508, "Invalid mail content", HttpStatus.BAD_REQUEST),
    MAIL_INVALID_SUBJECT(2509, "Invalid mail subject", HttpStatus.BAD_REQUEST),
    MAIL_INVALID_MESSAGE(2510, "Invalid mail message", HttpStatus.BAD_REQUEST),
    JWT_INVALID(9001, "Invalid JWT token", HttpStatus.NOT_FOUND),
    JWT_EXPIRED(9002, "JWT token has expired", HttpStatus.BAD_REQUEST),
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
