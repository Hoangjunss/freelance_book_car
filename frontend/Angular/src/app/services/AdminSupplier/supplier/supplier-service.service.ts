import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environment';
import { CreateHotelResponse } from '../../../models/response/product/hotel/hotel/create-hotel-response';
import { Apiresponse } from '../../../models/response/apiresponse';
import { map, Observable } from 'rxjs';
import { CreateProductRequest } from '../../../models/AdminSupplier/request/products/create-product-resquest';
import { Product } from '../../../models/AdminSupplier/response/products/product';
import { ProductGroup } from '../../../models/AdminSupplier/response/danhmucsanpham/product-group';
import { Supplier } from '../../../models/AdminSupplier/response/supplier/supplier';


@Injectable({
  providedIn: 'root'
})
export class SupplierServiceService {

 private baseUrl = `${environment.apiBaseUrl}/api/v1/hotel`;
 
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
     getProductGroup(): Observable<Supplier[]> {
       const headers = this.createAuthorizationHeader();
       return this.httpClient.patch<Apiresponse<Supplier[]>>(`${this.baseUrl}`,  {headers}).pipe(
         map((response: Apiresponse<Supplier[]>) => {
           if (response.success) {
             return response.data;
           } else {
             throw new Error(response.message);
           }
         })
       );
     }
}
