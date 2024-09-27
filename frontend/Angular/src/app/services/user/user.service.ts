import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Apiresponse } from '../../models/response/apiresponse';

import { map } from 'rxjs/operators';
import { registerUserResponse } from '../../models/response/user/register-response';
import { loginUserResponse } from '../../models/response/user/login-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private baseURL = "http://localhost:8080/auth/";
  constructor(private http:HttpClient,private router:Router) { }

  registerUser(formData: FormData): Observable<registerUserResponse> {
    return this.http.post<Apiresponse<registerUserResponse>>(`${this.baseURL}registration`, formData).pipe(
      map((response: Apiresponse<registerUserResponse>) => { 
        if (response.success) {
          return response.data; 
        } else {
          throw new Error('Registration failed'); // Ném lỗi nếu không thành công
        }
      })
    );
  }

  loginUser(formData: FormData): Observable<loginUserResponse> {
    return this.http.post<Apiresponse<loginUserResponse>>(`${this.baseURL}login`, formData).pipe(
      map((response: Apiresponse<loginUserResponse>) => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error('Login failed');
        }
      })
    );
  }
  
  
}
