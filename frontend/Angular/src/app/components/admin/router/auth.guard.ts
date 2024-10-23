import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { UserService } from "../../../services/user/user.service";
import { catchError, map, Observable, of } from "rxjs";
import { GetCurrentUserResponse } from "../../../models/response/user/get-current-user-response";


@Injectable({
    providedIn: 'root'
  })
  export class AuthGuard implements CanActivate {
  
    constructor(private authService: UserService, private router: Router) { }
  
    canActivate(): Observable<boolean> {
        const userData = localStorage.getItem('currentUser');
        if (userData) {
          try {
            const parsedData = JSON.parse(userData);
            const currentUser: GetCurrentUserResponse = {
              id: parsedData.id,
              name: parsedData.name,
              role: parsedData.role
            };
    
            if (currentUser.role === 'ADMIN') {
              return of(true);
            } else {
              this.router.navigate(['/login']);
              return of(false);
            }
          } catch (error) {
            console.error("Error parsing user data from localStorage", error);
            this.router.navigate(['/login']);
            return of(false);
          }
        } else {
          this.router.navigate(['/login']);
          return of(false);
        }
      }
      
  }