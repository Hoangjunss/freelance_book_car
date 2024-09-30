import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateTicketRequest } from '../../../../models/request/product/ticket/ticket/create-ticket-request';
import { CreateTicketResponse } from '../../../../models/response/product/ticket/ticket/create-ticket-response';
import { UpdateTicketRequest } from '../../../../models/request/product/ticket/ticket/update-ticket-request';
import { UpdateTicketResponse } from '../../../../models/response/product/ticket/ticket/update-ticket-response';
import { GetTicketResponse } from '../../../../models/response/product/ticket/ticket/get-ticket-response';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private baseUrl = 'http://localhost:8080/ticket';

  constructor(private httpClient: HttpClient) { }

  createTicket(formData: FormData): Observable<CreateTicketResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<CreateTicketResponse>>(`${this.baseUrl}`, formData, {headers}).pipe(
      map((response: Apiresponse<CreateTicketResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateTicket(formData: FormData): Observable<UpdateTicketResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.patch<Apiresponse<UpdateTicketResponse>>(`${this.baseUrl}`, formData, {headers}).pipe(
      map((response: Apiresponse<UpdateTicketResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTicket(id: number): Observable<GetTicketResponse> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTicketResponse>>(`${this.baseUrl}?id=${id}`, {headers}).pipe(
      map((response: Apiresponse<GetTicketResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getTicketByIdTourism(id:number):  Observable<GetTicketResponse[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTicketResponse[]>>(`${this.baseUrl}/ticket?idTourism=${id}`, {headers}).pipe(
      map((response: Apiresponse<GetTicketResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllTickets(): Observable<GetTicketResponse[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<GetTicketResponse[]>>(`${this.baseUrl}/all`, {headers}).pipe(
      map((response: Apiresponse<GetTicketResponse[]>) => {
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
