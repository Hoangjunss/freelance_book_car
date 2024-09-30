import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateTourismResponse } from '../../../../models/response/product/ticket/tourism/create-tourism-response';
import { UpdateTourismResponse } from '../../../../models/response/product/ticket/tourism/update-tourism-response';
import { GetTourismResponse } from '../../../../models/response/product/ticket/tourism/get-tourism-response';

@Injectable({
  providedIn: 'root'
})
export class TourismService {

  private baseUrl = 'http://localhost:8080/tourism';

  constructor(private httpClient: HttpClient) { }

  createTour(formData: FormData): Observable<CreateTourismResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<CreateTourismResponse>>(`${this.baseUrl}`, formData, {headers}).pipe(
      map((response: Apiresponse<CreateTourismResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateTour(formData: FormData): Observable<UpdateTourismResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateTourismResponse>>(`${this.baseUrl}`, formData, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTourismResponse>>(`${this.baseUrl}?id=${id}`, {headers}).pipe(
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
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTourismResponse[]>>(`${this.baseUrl}`, {headers}).pipe(
      map((response: Apiresponse<GetTourismResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTourismByCategory(category: string): Observable<GetTourismResponse[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTourismResponse[]>>(`${this.baseUrl}/${category}`, {headers}).pipe(
      map((response: Apiresponse<GetTourismResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTourismDetailById(id: number): Observable<GetTourismResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTourismResponse>>(`${this.baseUrl}/detail?id=${id}`, {headers}).pipe(
      map((response: Apiresponse<GetTourismResponse>) => {
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
