import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StatisticYear } from '../../models/response/statistics/StatisticYear';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../models/response/apiresponse';
import { StatisticMonthYear } from '../../models/response/statistics/StatisticMonthYear';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {
  private baseUrl = 'http://localhost:8080/statistic';

  constructor(private httpClient: HttpClient) { }

  getStatisticYear(year: number): Observable<StatisticYear> {
    return this.httpClient.get<Apiresponse<StatisticYear>>(`${this.baseUrl}/year?year=${year}`).pipe(
      map((response: Apiresponse<StatisticYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticMonthYear(month: number, year: number){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/monthyear?month=${month}&year=${year}`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

}
