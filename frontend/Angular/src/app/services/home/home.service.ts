import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { map, Observable } from 'rxjs';
import { GetPageResponse } from '../../models/response/home/get-page-response';
import { Apiresponse } from '../../models/response/apiresponse';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private baseUrl = 'http://localhost:8080/api/v1/page/';
  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) { }

  getHome():Observable<GetPageResponse> {
    return this.http.get<Apiresponse<GetPageResponse>>(`${this.baseUrl}home`).pipe(
      map((response: Apiresponse<GetPageResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getDetail():Observable<GetPageResponse> {
    return this.http.get<Apiresponse<GetPageResponse>>(`${this.baseUrl}detail`).pipe(
      map((response: Apiresponse<GetPageResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getFooter():Observable<GetPageResponse> {
    return this.http.get<Apiresponse<GetPageResponse>>(`${this.baseUrl}footer`).pipe(
      map((response: Apiresponse<GetPageResponse>) => {
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
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }
}
