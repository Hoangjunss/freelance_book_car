import { HttpClient, HttpHeaders } from '@angular/common/http';
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
import { UpdateQuantityTour } from '../../models/response/booking/update-quantity-tour';
import { UpdateQuantityTourism } from '../../models/response/booking/update-quantity-tourism';
import { UpdateQuantityHotel } from '../../models/response/booking/update-quantity-hotel';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private httpClient:HttpClient) { }

  private baseUrl = 'http://localhost:8080/api/v1/booking';

  createBooking(createBookingRequest: CreateBookingRequest): Observable<CreateBookingResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<CreateBookingResponse>>(`${this.baseUrl}`, createBookingRequest, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.put<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}`, updateBookingRequest, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelBookingResponse>>(`${this.baseUrl}?id=${id}`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelBookingResponse[]>>(`${this.baseUrl}/all`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelBookingResponse[]>>(`${this.baseUrl}/type?type=${type}`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}?id=${id}&type=${type}`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/tour`,formData, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/hotel`,formData, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/tourism`,formData, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetBookingResponse>>(`${this.baseUrl}/user?idUser=${id}`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetBookingDetailResponse[]>>(`${this.baseUrl}/detail?idBooking=${id}`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateBookingResponse>>(`${this.baseUrl}/type?type=${type}&id=${id}`, null, {headers}).pipe(
      map((response: Apiresponse<UpdateBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateQuantityTour(formData : FormData): Observable<UpdateQuantityTour> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateQuantityTour>>(`${this.baseUrl}/tour`,formData, {headers}).pipe(
      map((response: Apiresponse<UpdateQuantityTour>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateQuantityTourism(formData : FormData): Observable<UpdateQuantityTourism> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateQuantityTourism>>(`${this.baseUrl}/tourism`,formData, {headers}).pipe(
      map((response: Apiresponse<UpdateQuantityTourism>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateQuantityHotel(formData : FormData): Observable<UpdateQuantityHotel> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateQuantityHotel>>(`${this.baseUrl}/hotel`,formData, {headers}).pipe(
      map((response: Apiresponse<UpdateQuantityHotel>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    console.log(token);
    if (token) {
      console.log('Token found in local store:', token);
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }
  
}
