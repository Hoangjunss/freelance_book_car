import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { CreateHotelRequest } from '../../../../models/request/product/hotel/hotel/create-hotel-request';
import { CreateHotelResponse } from '../../../../models/response/product/hotel/hotel/create-hotel-response';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { UpdateHotelRequest } from '../../../../models/request/product/hotel/hotel/update-hotel-request';
import { UpdateHotelResponse } from '../../../../models/response/product/hotel/hotel/update-hotel-response';
import { GetHotelResponse } from '../../../../models/response/product/hotel/hotel/get-hotel-response';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private baseUrl = 'http://localhost:8080/hotel';

  constructor(private httpClient: HttpClient) { }

  createHotel(formData: FormData): Observable<CreateHotelResponse> {
    return this.httpClient.post<Apiresponse<CreateHotelResponse>>(`${this.baseUrl}`, formData).pipe(
      map((response: Apiresponse<CreateHotelResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateHotel(updateHotelRequest: UpdateHotelRequest): Observable<UpdateHotelResponse> {
    return this.httpClient.put<Apiresponse<UpdateHotelResponse>>(`${this.baseUrl}`, updateHotelRequest).pipe(
      map((response: Apiresponse<UpdateHotelResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getHotel(id: number): Observable<GetHotelResponse> {
    return this.httpClient.get<Apiresponse<GetHotelResponse>>(`${this.baseUrl}?id=${id}`).pipe(
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
    return this.httpClient.get<Apiresponse<GetHotelResponse[]>>(`${this.baseUrl}`).pipe(
      map((response: Apiresponse<GetHotelResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }
}
