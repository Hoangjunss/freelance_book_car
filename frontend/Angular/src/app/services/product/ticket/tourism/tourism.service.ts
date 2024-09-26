import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateTourismRequest } from '../../../../models/request/product/ticket/tourism/create-tourism-request';
import { CreateTourismResponse } from '../../../../models/response/product/ticket/tourism/create-tourism-response';
import { UpdateTourismRequest } from '../../../../models/request/product/ticket/tourism/update-tourism-request';
import { UpdateTourismResponse } from '../../../../models/response/product/ticket/tourism/update-tourism-response';
import { GetTourismResponse } from '../../../../models/response/product/ticket/tourism/get-tourism-response';

@Injectable({
  providedIn: 'root'
})
export class TourismService {

  private baseUrl = 'http://localhost:8080/tourism';

  constructor(private httpClient: HttpClient) { }

  createTour(formData: FormData): Observable<CreateTourismResponse> {
    return this.httpClient.post<Apiresponse<CreateTourismResponse>>(`${this.baseUrl}`, formData).pipe(
      map((response: Apiresponse<CreateTourismResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  // createTour(formData: FormData): Observable<CreateTourismResponse> {
  //   return this.httpClient.post<ApiResponse<CreateTourismResponse>>(`${this.baseUrl}`, formData).pipe(
  //     map((response: ApiResponse<CreateTourismResponse>) => {
  //       if (response.success) {
  //         return response.data;
  //       } else {
  //         throw new Error(response.message);
  //       }
  //     })
  //   );
  // }

  updateTour(updateTourRequest: UpdateTourismRequest): Observable<UpdateTourismResponse> {
    return this.httpClient.put<Apiresponse<UpdateTourismResponse>>(`${this.baseUrl}`, updateTourRequest).pipe(
      map((response: Apiresponse<UpdateTourismResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTour(id: number): Observable<GetTourismResponse> {
    return this.httpClient.get<Apiresponse<GetTourismResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetTourismResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllTourism(): Observable<GetTourismResponse[]> {
    return this.httpClient.get<Apiresponse<GetTourismResponse[]>>(`${this.baseUrl}`).pipe(
      map((response: Apiresponse<GetTourismResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

}
