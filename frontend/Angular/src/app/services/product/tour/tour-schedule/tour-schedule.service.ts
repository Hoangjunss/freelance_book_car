import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateTourScheduleRequest } from '../../../../models/request/product/tour/tour-schedule/create-tour-schedule-request';
import { CreateTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/create-tour-schedule-response';
import { UpdateTourScheduleRequest } from '../../../../models/request/product/tour/tour-schedule/update-tour-schedule-request';
import { UpdateTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/update-tour-schedule-response';
import { GetTourScheduleResponse } from '../../../../models/response/product/tour/tour-schedule/get-tour-schedule-response';

@Injectable({
  providedIn: 'root'
})
export class TourScheduleService {

  private baseUrl = 'http://localhost:8080/tour-schedule';

  constructor(private httpClient: HttpClient) { }

  createSchedule(formData: FormData): Observable<CreateTourScheduleResponse> {
    return this.httpClient.post<Apiresponse<CreateTourScheduleResponse>>(`${this.baseUrl}`, formData).pipe(
      map((response: Apiresponse<CreateTourScheduleResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateSchedule(formData: FormData): Observable<UpdateTourScheduleResponse> {
    return this.httpClient.patch<Apiresponse<UpdateTourScheduleResponse>>(`${this.baseUrl}`, formData).pipe(
      map((response: Apiresponse<UpdateTourScheduleResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllTourSchedule(): Observable<UpdateTourScheduleRequest[]> {
    return this.httpClient.get<Apiresponse<GetTourScheduleResponse[]>>(`${this.baseUrl}/all`).pipe(
      map((response: Apiresponse<GetTourScheduleResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );

  }

  getSchedule(id: number): Observable<GetTourScheduleResponse> {
    return this.httpClient.get<Apiresponse<GetTourScheduleResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetTourScheduleResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTourScheduleByidTour(id:number):Observable<GetTourScheduleResponse[]> {
    return this.httpClient.get<Apiresponse<GetTourScheduleResponse[]>>(`${this.baseUrl}/tour?idTour=${id}`).pipe(
      map((response: Apiresponse<GetTourScheduleResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  
}
