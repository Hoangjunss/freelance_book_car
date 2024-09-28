import { HttpClient } from '@angular/common/http';
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

  createTicket(createTicketRequest: CreateTicketRequest): Observable<CreateTicketResponse> {
    return this.httpClient.post<Apiresponse<CreateTicketResponse>>(`${this.baseUrl}`, createTicketRequest).pipe(
      map((response: Apiresponse<CreateTicketResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateTicket(updateTicketRequest: UpdateTicketRequest): Observable<UpdateTicketResponse> {
    return this.httpClient.put<Apiresponse<UpdateTicketResponse>>(`${this.baseUrl}`, updateTicketRequest).pipe(
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
    return this.httpClient.get<Apiresponse<GetTicketResponse>>(`${this.baseUrl}?id=${id}`).pipe(
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
    return this.httpClient.get<Apiresponse<GetTicketResponse[]>>(`${this.baseUrl}/ticket?idTourism=${id}`).pipe(
      map((response: Apiresponse<GetTicketResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
}
