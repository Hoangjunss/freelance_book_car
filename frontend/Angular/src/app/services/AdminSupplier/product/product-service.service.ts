import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environment';
import { CreateHotelResponse } from '../../../models/response/product/hotel/hotel/create-hotel-response';
import { Apiresponse } from '../../../models/response/apiresponse';
import { map, Observable } from 'rxjs';
import { CreateProductRequest } from '../../../models/AdminSupplier/request/products/create-product-resquest';
import { Product } from '../../../models/AdminSupplier/response/products/product';
import { Page } from '../../../models/page';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {
  private baseUrl = `${environment.apiBaseUrl}`;

  constructor(private httpClient: HttpClient) { }
  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('token');
    if (token) {
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }
  createProduct(formData: FormData): Observable<CreateProductRequest> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.post<Apiresponse<CreateProductRequest>>(`${this.baseUrl}/supplier/products`, formData, {headers}).pipe(
        map((response: Apiresponse<CreateProductRequest>) => {
          if (response.success) {
            return response.data;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }
    updateProduct(formData: FormData): Observable<CreateProductRequest> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.patch<Apiresponse<CreateProductRequest>>(`${this.baseUrl}`, formData, {headers}).pipe(
        map((response: Apiresponse<CreateProductRequest>) => {
          if (response.success) {
            return response.data;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }
    getProductId(id: number): Observable<Product> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<Product>>(`${this.baseUrl}/products/${id}`, {headers}).pipe(
        map((response: Apiresponse<Product>) => {
          if (response.success) {
            return response.data;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }

    getAllProductPending(page:number): Observable<Product[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<Page<Product[]>>>(`${this.baseUrl}/supplier/products?productVerifyStatus=Pending&page=${page}&size=2`, {headers}).pipe(
        map((response: Apiresponse<Page<Product[]>>) => {
          if (response.success) {
            return response.data.content;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }
    getAllProductAccess(page:number): Observable<Product[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<Page<Product[]>>>(`${this.baseUrl}/supplier/products?productVerifyStatus=Access&page=${page}&size=2`, {headers}).pipe(
        map((response: Apiresponse<Page<Product[]>>) => {
          if (response.success) {
            return response.data.content;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }

}
