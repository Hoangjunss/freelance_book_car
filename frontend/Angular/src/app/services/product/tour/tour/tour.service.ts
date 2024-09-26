import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateTourRequest } from '../../../../models/request/product/tour/tour/create-tour-request';
import { CreateTourResponse } from '../../../../models/response/product/tour/tour/create-tour-response';
import { UpdateTourRequest } from '../../../../models/request/product/tour/tour/update-tour-request';
import { UpdateTourResponse } from '../../../../models/response/product/tour/tour/update-tour-response';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';

@Injectable({
  providedIn: 'root'
})
export class TourService {

  private baseUrl = 'http://localhost:8080/tour';

  constructor(private httpClient: HttpClient) { }

  createTour(fromData: FormData): Observable<CreateTourResponse> {
    return this.httpClient.post<Apiresponse<CreateTourResponse>>(`${this.baseUrl}`, fromData).pipe(
      map((response: Apiresponse<CreateTourResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateTour(fomrData: FormData): Observable<UpdateTourResponse> {
    return this.httpClient.patch<Apiresponse<UpdateTourResponse>>(`${this.baseUrl}`, fomrData).pipe(
      map((response: Apiresponse<UpdateTourResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTour(id: number): Observable<GetTourResponse> {
    return this.httpClient.get<Apiresponse<GetTourResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetTourResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllTour(): Observable<GetTourResponse[]> {
    return this.httpClient.get<Apiresponse<GetTourResponse[]>>(`${this.baseUrl}`).pipe(
      map((response: Apiresponse<GetTourResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTourByCategory(category: string): Observable<GetTourResponse[]> {
    return this.httpClient.get<Apiresponse<GetTourResponse[]>>(`${this.baseUrl}/${category}`).pipe(
      map((response: Apiresponse<GetTourResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTourById(id: number): Observable<GetTourResponse> {
    return this.httpClient.get<Apiresponse<GetTourResponse>>(`${this.baseUrl}/id/${id}`).pipe(
      map((response: Apiresponse<GetTourResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
}
