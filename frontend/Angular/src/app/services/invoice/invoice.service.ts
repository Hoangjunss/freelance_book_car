import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateInvoiceRequest } from '../../models/request/invoice/create-invoice-request';
import { map, Observable } from 'rxjs';
import { CreateInvoiceResponse } from '../../models/response/invoice/create-invoice-response';
import { Apiresponse } from '../../models/response/apiresponse';
import { GetInvoiceResponse } from '../../models/response/invoice/get-invoice-response';
import { UpdateInvoiceRequest } from '../../models/request/invoice/update-invoice-request';
import { UpdateInvoiceResponse } from '../../models/response/invoice/update-invoice-response';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  constructor(private httpClient: HttpClient) { }

  private baseUrl = 'http://localhost:8080/invoice';

  createInvoice(createInvoiceRequest: CreateInvoiceRequest): Observable<CreateInvoiceResponse> {
    return this.httpClient.post<Apiresponse<CreateInvoiceResponse>>(`${this.baseUrl}`, createInvoiceRequest).pipe(
      map((response: Apiresponse<CreateInvoiceResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateInvoice(updateInvoiceRequest: UpdateInvoiceRequest): Observable<UpdateInvoiceResponse> {
    return this.httpClient.put<Apiresponse<UpdateInvoiceResponse>>(`${this.baseUrl}`, updateInvoiceRequest).pipe(
      map((response: Apiresponse<UpdateInvoiceResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getInvoice(id: number): Observable<GetInvoiceResponse> {
    return this.httpClient.get<Apiresponse<GetInvoiceResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetInvoiceResponse>) => {
        if (response.success) {
          return response.data; // Trả về dữ liệu nếu thành công
        } else {
          throw new Error(response.message); // Ném lỗi nếu không thành công
        }
      })
    );
  }
}
