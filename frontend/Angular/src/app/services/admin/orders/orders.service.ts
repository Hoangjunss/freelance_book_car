import { PageHomeComponent } from './../../../components/admin/page/page-home/page-home.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environment';
import { isPlatformBrowser } from '@angular/common';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../../../models/response/apiresponse';
import { OrderDTO } from '../../../models/AdminSupplier/response/orders/orders';
import { Page } from '../../../models/page';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

      constructor(private httpClient:HttpClient, @Inject(PLATFORM_ID) private platformId: Object) { }
    
      private baseUrl = `${environment.apiBaseUrl}/supplier/orders`;

      updateStatusOrder(idOrder: number, idStatus: number): Observable<any>{
        const headers = this.createAuthorizationHeader();
        return this.httpClient.put<Apiresponse<any>>(`${this.baseUrl}?idOrder=${idOrder}&idStatus=${idStatus}`, {headers}).pipe(
                  map((response: Apiresponse<any>) => {
                          if (response.success) {
                            return response.data;
                          } else {
                            throw new Error(response.message);
                          }
                        })
                );
      }

      getOrderOfSupplierAccess(): Observable<OrderDTO[]>{
        const headers = this.createAuthorizationHeader();
        return this.httpClient.get<Apiresponse<Page<OrderDTO[]>>>(`${this.baseUrl}?OrderStatus=Access&page=0&size=10`, {headers}).pipe(
                  map((response: Apiresponse<Page<OrderDTO[]>>) => {
                          if (response.success) {
                            return response.data.content;
                          } else {
                            throw new Error(response.message);
                          }
                        })
                );
      }
      getOrderOfSupplierPending(): Observable<OrderDTO[]>{
        const headers = this.createAuthorizationHeader();
        return this.httpClient.get<Apiresponse<Page<OrderDTO[]>>>(`${this.baseUrl}?OrderStatus=Pending&page=0&size=10`, {headers}).pipe(
                  map((response: Apiresponse<Page<OrderDTO[]>>) => {
                          if (response.success) {
                            return response.data.content;
                          } else {
                            throw new Error(response.message);
                          }
                        })
                );
      }


      getOrderById(id:number): Observable<OrderDTO>{
        const headers = this.createAuthorizationHeader();
        return this.httpClient.get<Apiresponse<OrderDTO>>(`${this.baseUrl}/getId?id=${id}`, {headers}).pipe(
          map((response: Apiresponse<OrderDTO>) => {
                  if (response.success) {
                    return response.data;
                  } else {
                    throw new Error(response.message);
                  }
                })
        );
      }

      

        private createAuthorizationHeader(): HttpHeaders {
          let token = null;
      
          if (isPlatformBrowser(this.platformId)) {
            token = localStorage.getItem('token');
          }
          if (token) {
            console.log(token);
            return new HttpHeaders().set('Authorization', `Bearer ${token}`);
          }
          else {
            console.log('Token not found in local store');
          }
          return new HttpHeaders();
        }
}
