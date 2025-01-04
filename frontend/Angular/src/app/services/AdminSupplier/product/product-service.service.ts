import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environment';
import { CreateHotelResponse } from '../../../models/response/product/hotel/hotel/create-hotel-response';
import { Apiresponse } from '../../../models/response/apiresponse';
import { map, Observable } from 'rxjs';
import { CreateProductRequest } from '../../../models/AdminSupplier/request/products/create-product-resquest';
import { Product } from '../../../models/AdminSupplier/response/products/product';
import { Page } from '../../../models/page';
import { ProductGroup } from '../../../models/AdminSupplier/response/danhmucsanpham/product-group';
import { ProductType } from '../../../models/AdminSupplier/response/products/productType';

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
      return this.httpClient.patch<Apiresponse<CreateProductRequest>>(`${this.baseUrl}/products`, formData, {headers}).pipe(
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
    getAdminAllProductPending(page:number): Observable<Product[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<Page<Product[]>>>(`${this.baseUrl}/admin/product?page=${page}&size=5&productVerifyStatus=Pending&isVerifyStatus=true`, {headers}).pipe(
        map((response: Apiresponse<Page<Product[]>>) => {
          if (response.success) {
            return response.data.content;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }
    getAdminAllProductAccess(page:number): Observable<Product[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<Page<Product[]>>>(`${this.baseUrl}/admin/product?page=${page}&size=5&productVerifyStatus=Access&isVerifyStatus=true`, {headers}).pipe(
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
    GetProductGroup(): Observable<ProductGroup[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<ProductGroup[]>>(`${this.baseUrl}/supplier/productGroup`, {headers}).pipe(
        map((response: Apiresponse<ProductGroup[]>) => {
          if (response.success) {
            return response.data;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }
    GetProductType(productGroupId:number): Observable<ProductType[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<ProductType[]>>(`${this.baseUrl}/product-types?productGroupId=${productGroupId}`, {headers}).pipe(
        map((response: Apiresponse<ProductType[]>) => {
          if (response.success) {
            return response.data;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }

    AccecptProduct(productId:number): Observable<any[]> {
      const headers = this.createAuthorizationHeader();
      return this.httpClient.get<Apiresponse<any[]>>(`${this.baseUrl}/admin/product?id=${productId}&productVerifyStatus=Access`, {headers}).pipe(
        map((response: Apiresponse<any[]>) => {
          if (response.success) {
            return response.data;
          } else {
            throw new Error(response.message);
          }
        })
      );
    }
    
}
