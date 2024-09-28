import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateBookingRequest } from '../../models/request/booking/create-booking-request';
import { map, Observable } from 'rxjs';
import { CreateBookingResponse } from '../../models/response/booking/create-booking-response';
import { Apiresponse } from '../../models/response/apiresponse';
import { UpdateBookingRequest } from '../../models/request/booking/update-booking-request';
import { UpdateBookingResponse } from '../../models/response/booking/update-booking-response';
import { GetHotelBookingResponse } from '../../models/response/product/hotel/hotel-booking/get-hotelbooking-response';
import { AddBookingTourRequest } from '../../models/request/booking/add-booking-tour-request';
import { GetBookingResponse } from '../../models/response/booking/get-booking-response';
import { GetBookingDetailResponse } from '../../models/response/booking/get-booking-detail-response';

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
  
  getByType(type: string): Observable<GetHotelBookingResponse[]> {
    return this.httpClient.get<Apiresponse<GetHotelBookingResponse[]>>(`${this.baseUrl}?type=${type}`).pipe(
      map((response: Apiresponse<GetHotelBookingResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  adminSetTypeBooking(id: number, type: string):  Observable<UpdateBookingResponse>{
    return this.httpClient.get<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}?id=${id}&type=${type}`).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  addBookingTour(formData : FormData): Observable<UpdateBookingResponse>{
    return this.httpClient.post<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/tour`,formData).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
  addBookingHotel(formData : FormData): Observable<UpdateBookingResponse>{
    return this.httpClient.post<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/hotel`,formData).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
  addBookingTourism(formData : FormData): Observable<UpdateBookingResponse>{
    return this.httpClient.post<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/tourism`,formData).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getBookingByUser(id: number): Observable<GetBookingResponse> {
    return this.httpClient.get<Apiresponse<GetBookingResponse>>(`${this.baseUrl}/user?idUser=${id}`).pipe(
      map((response: Apiresponse<GetBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
  getDetailBooking(id: number): Observable<GetBookingDetailResponse[]> {
    return this.httpClient.get<Apiresponse<GetBookingDetailResponse[]>>(`${this.baseUrl}/detail?idBooking=${id}`).pipe(
      map((response: Apiresponse<GetBookingDetailResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateTypeBooking(type: string,id: number): Observable<UpdateBookingResponse> {
    return this.httpClient.patch<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/type?type=${type}&id=${id}`, null).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
  
}
