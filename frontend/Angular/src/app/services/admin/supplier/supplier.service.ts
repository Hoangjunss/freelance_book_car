import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { environment } from '../../environment';
import { isPlatformBrowser } from '@angular/common';
import { map, Observable } from 'rxjs';
import { SupplyCreateDTOS } from '../../../models/admin/request/supplier/SupplyCreateDTOS';
import { Apiresponse } from '../../../models/response/apiresponse';
import { SupplyUpdateDTOS } from '../../../models/admin/request/supplier/SupplyUpdateDTOS';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

    constructor(private httpClient:HttpClient, @Inject(PLATFORM_ID) private platformId: Object) { }
  
    private baseUrl = `${environment.apiBaseUrl}`;

    createSupplier(formData: FormData): Observable<SupplyCreateDTOS> {
        const headers = this.createAuthorizationHeader();
        return this.httpClient.post<Apiresponse<SupplyCreateDTOS>>(`${this.baseUrl}`, formData, {headers}).pipe(
          map((response: Apiresponse<SupplyCreateDTOS>) => {
                  if (response.success) {
                    return response.data;
                  } else {
                    throw new Error(response.message);
                  }
                })
        );
      }

    updateSupplier(formData: FormData): Observable<SupplyUpdateDTOS> {
      const headers = this.createAuthorizationHeader();
        return this.httpClient.patch<Apiresponse<SupplyUpdateDTOS>>(`${this.baseUrl}`, formData, {headers}).pipe(
          map((response: Apiresponse<SupplyUpdateDTOS>) => {
                  if (response.success) {
                    return response.data;
                  } else {
                    throw new Error(response.message);
                  }
                })
        );
  
    }

    getSupplier(id: number): Observable<SupplyUpdateDTOS>{
      const headers = this.createAuthorizationHeader();
        return this.httpClient.get<Apiresponse<SupplyUpdateDTOS>>(`${this.baseUrl}?id=${id}`, {headers}).pipe(
          map((response: Apiresponse<SupplyUpdateDTOS>) => {
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
