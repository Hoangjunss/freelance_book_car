import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreateVoucherRequest } from '../../../../models/request/product/voucher/voucher/create-voucher-request';
import { CreateVoucherResponse } from '../../../../models/response/product/voucher/voucher/create-voucher-response';
import { UpdateVoucherRequest } from '../../../../models/request/product/voucher/voucher/update-voucher-request';
import { UpdateVoucherResponse } from '../../../../models/response/product/voucher/voucher/update-voucher-response';
import { GetVoucherResponse } from '../../../../models/response/product/voucher/voucher/get-voucher-response';

@Injectable({
  providedIn: 'root'
})
export class VoucherService {

  private baseUrl = 'http://localhost:8080/voucher';

  constructor(private httpClient: HttpClient) { }

  createVoucher(formData: FormData): Observable<CreateVoucherResponse> {
    return this.httpClient.post<Apiresponse<CreateVoucherResponse>>(`${this.baseUrl}`, formData).pipe(
      map((response: Apiresponse<CreateVoucherResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateVoucher(formData: FormData): Observable<UpdateVoucherResponse> {
    return this.httpClient.patch<Apiresponse<UpdateVoucherResponse>>(`${this.baseUrl}`, formData).pipe(
      map((response: Apiresponse<UpdateVoucherResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getVoucher(id: number): Observable<GetVoucherResponse> {
    return this.httpClient.get<Apiresponse<GetVoucherResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetVoucherResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAll(): Observable<GetVoucherResponse[]> {
    return this.httpClient.get<Apiresponse<GetVoucherResponse[]>>(`${this.baseUrl}/all`).pipe(
      map((response: Apiresponse<GetVoucherResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }
}
