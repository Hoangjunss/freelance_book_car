import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { map, Observable } from 'rxjs';
import { CreateHotelResponse } from '../../../../models/response/product/hotel/hotel/create-hotel-response';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { UpdateHotelResponse } from '../../../../models/response/product/hotel/hotel/update-hotel-response';
import { GetHotelResponse } from '../../../../models/response/product/hotel/hotel/get-hotel-response';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private baseUrl = 'http://localhost:8080/api/v1/hotel';

  constructor(private httpClient: HttpClient) { }

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


  createHotel(formData: FormData): Observable<CreateHotelResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<CreateHotelResponse>>(`${this.baseUrl}`, formData, {headers}).pipe(
      map((response: Apiresponse<CreateHotelResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }


  updateHotel(formData: FormData): Observable<UpdateHotelResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateHotelResponse>>(`${this.baseUrl}`, formData, {headers}).pipe(
      map((response: Apiresponse<UpdateHotelResponse>) => {
        if (response.success) {
          console.log(`Service: ` + JSON.stringify(response.data));
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getHotel(id: number): Observable<GetHotelResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelResponse>>(`${this.baseUrl}?id=${id}`, {headers}).pipe(
      map((response: Apiresponse<GetHotelResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllHotel(): Observable<GetHotelResponse[]> {
    const headers= this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelResponse[]>>(`${this.baseUrl}`, {headers}).pipe(
      map((response: Apiresponse<GetHotelResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getHotelByLocation(location: string): Observable<GetHotelResponse[]> {
    const headers= this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelResponse[]>>(`${this.baseUrl}/${location}`, {headers}).pipe(
      map((response: Apiresponse<GetHotelResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getHotelDetailById(id: number): Observable<GetHotelResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetHotelResponse>>(`${this.baseUrl}/id/${id}`, {headers}).pipe(
      map((response: Apiresponse<GetHotelResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }


}
