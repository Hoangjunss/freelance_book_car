import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateBookingRequest } from '../../models/request/booking/create-booking-request';
import { map, Observable } from 'rxjs';
import { CreateBookingResponse } from '../../models/response/booking/create-booking-response';
import { Apiresponse } from '../../models/response/apiresponse';
import { UpdateBookingRequest } from '../../models/request/booking/update-booking-request';
import { UpdateBookingResponse } from '../../models/response/booking/update-booking-response';
import { GetHotelBookingResponse } from '../../models/response/product/hotel/hotel-booking/get-hotelbooking-response';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private httpClient:HttpClient) { }

  private baseUrl = 'http://localhost:8080/booking';

  createBooking(createBookingRequest: CreateBookingRequest): Observable<CreateBookingResponse> {
    return this.httpClient.post<Apiresponse<CreateBookingResponse>>(`${this.baseUrl}`, createBookingRequest).pipe(
      map((response: Apiresponse<CreateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateBooking(updateBookingRequest: UpdateBookingRequest): Observable<UpdateBookingResponse>{
    return this.httpClient.put<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}`, updateBookingRequest).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getBooking(id: number): Observable<GetHotelBookingResponse>{
    return this.httpClient.get<Apiresponse<GetHotelBookingResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetHotelBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAll(): Observable<GetHotelBookingResponse[]> {
    return this.httpClient.get<Apiresponse<GetHotelBookingResponse[]>>(`${this.baseUrl}/all`).pipe(
      map((response: Apiresponse<GetHotelBookingResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }    
  
}
