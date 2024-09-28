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
          console.log(response.data);
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

  getStatisticMonthHotel(month: number, year: number){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/month/hotel?month=${month}&year=${year}`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticMonthTour(month: number, year: number){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/month/tour?month=${month}&year=${year}`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticMonthTourism(month: number, year: number){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/month/tourism?month=${month}&year=${year}`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticTodayHotel(){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/today/hotel`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticTodayTour(){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/today/tour`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticTodayTourism(){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/today/tourism`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticYesterdayHotel(){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/yesterday/hotel`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticYesterdayTour(){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/yesterday/tour`).pipe(
      map((response: Apiresponse<StatisticMonthYear>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    )
  }

  getStatisticYesterdayTourism(){
    return this.httpClient.get<Apiresponse<StatisticMonthYear>>(`${this.baseUrl}/yesterday/tourism`).pipe(
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
