import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../../models/response/apiresponse';
import { CreatePromotionRequest } from '../../../../models/request/product/voucher/promotion/create-promotion-request';
import { CreatePromotionResponse } from '../../../../models/response/product/voucher/promotion/create-promotion-response';
import { UpdatePromotionRequest } from '../../../../models/request/product/voucher/promotion/update-promotion-request';
import { UpdatePromotionResponse } from '../../../../models/response/product/voucher/promotion/update-promotion-response';
import { GetPromotionResponse } from '../../../../models/response/product/voucher/promotion/get-promotion-response';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  private baseUrl = 'http://localhost:8080/promotion';

  constructor(private httpClient: HttpClient) { }

  createPromotion(createPromotionRequest: CreatePromotionRequest): Observable<CreatePromotionResponse> {
    return this.httpClient.post<Apiresponse<CreatePromotionResponse>>(`${this.baseUrl}`, createPromotionRequest).pipe(
      map((response: Apiresponse<CreatePromotionResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updatePromotion(updatePromotionRequest: UpdatePromotionRequest): Observable<UpdatePromotionResponse> {
    return this.httpClient.patch<Apiresponse<UpdatePromotionResponse>>(`${this.baseUrl}`, updatePromotionRequest).pipe(
      map((response: Apiresponse<UpdatePromotionResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getPromotion(id: number): Observable<GetPromotionResponse> {
    return this.httpClient.get<Apiresponse<GetPromotionResponse>>(`${this.baseUrl}?id=${id}`).pipe(
      map((response: Apiresponse<GetPromotionResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAll(): Observable<GetPromotionResponse[]> {
    return this.httpClient.get<Apiresponse<GetPromotionResponse[]>>(`${this.baseUrl}/all`).pipe(
      map((response: Apiresponse<GetPromotionResponse[]>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }



  
}
