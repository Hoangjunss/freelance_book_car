import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateHotelBookingRequest } from '../../../../models/request/product/hotel/hotel-booking/create-hotelbooking-request';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateHotelBookingResponse } from '../../../../models/response/product/hotel/hotel-booking/create-hotelbooking-response';
import { UpdateHotelBookingRequest } from '../../../../models/request/product/hotel/hotel-booking/update-hotelbooking-request';
import { UpdateHotelBookingResponse } from '../../../../models/response/product/hotel/hotel-booking/update-hotelbooking-response';
import { GetHotelBookingResponse } from '../../../../models/response/product/hotel/hotel-booking/get-hotelbooking-response';

@Injectable({
  providedIn: 'root'
})
export class HotelbookingService {

  private baseUrl = 'http://localhost:8080/api/v1/hotel-booking';

  constructor(private httpClient: HttpClient) { }

  createBooking(createBookingRequest: CreateHotelBookingRequest): Observable<CreateHotelBookingResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<CreateHotelBookingResponse>>(`${this.baseUrl}`, createBookingRequest, {headers}).pipe(
      map((response: Apiresponse<CreateHotelBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateBooking(updateBookingRequest: UpdateHotelBookingRequest): Observable<UpdateHotelBookingResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.put<Apiresponse<UpdateHotelBookingResponse>>(`${this.baseUrl}`, updateBookingRequest, {headers}).pipe(
      map((response: Apiresponse<UpdateHotelBookingResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getBooking(id: number): Observable<GetHotelBookingResponse> {
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


  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }
}
