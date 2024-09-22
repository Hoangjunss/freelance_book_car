import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateTourScheduleStatusRequest } from '../../../../models/request/product/tour/tour-schedule-status/create-tour-schedule-status-request';
import { CreateTourScheduleStatusResponse } from '../../../../models/response/product/tour/tour-schedule-status/create-tour-schedule-status-request';
import { UpdateTourScheduleStatusRequest } from '../../../../models/request/product/tour/tour-schedule-status/update-tour-schedule-status-request';
import { UpdateTourScheduleStatusResponse } from '../../../../models/response/product/tour/tour-schedule-status/update-tour-schedule-status-request';
import { GetTourScheduleStatusResponse } from '../../../../models/response/product/tour/tour-schedule-status/get-tour-schedule-status-response';

@Injectable({
  providedIn: 'root'
})
export class TourScheduleStatusService {

  private baseUrl = 'http://localhost:8080/tour-schedule-status';

  constructor(private httpClient: HttpClient) { }

  createScheduleStatus(createScheduleStatusRequest: CreateTourScheduleStatusRequest): Observable<CreateTourScheduleStatusResponse> {
    return this.httpClient.post<Apiresponse<CreateTourScheduleStatusResponse>>(`${this.baseUrl}`, createScheduleStatusRequest).pipe(
      map((response: Apiresponse<CreateTourScheduleStatusResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateScheduleStatus(updateScheduleStatusRequest: UpdateTourScheduleStatusRequest): Observable<UpdateTourScheduleStatusResponse> {
    return this.httpClient.put<Apiresponse<UpdateTourScheduleStatusResponse>>(`${this.baseUrl}`, updateScheduleStatusRequest).pipe(
      map((response: Apiresponse<UpdateTourScheduleStatusResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getScheduleStatus(id: number): Observable<GetTourScheduleStatusResponse> {
    return this.httpClient.get<Apiresponse<GetTourScheduleStatusResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetTourScheduleStatusResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
}
