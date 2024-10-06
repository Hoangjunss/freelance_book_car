import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { UserService } from "../../../services/user/user.service";
import { catchError, map, Observable, of } from "rxjs";


@Injectable({
    providedIn: 'root'
  })
  export class AuthGuard implements CanActivate {
  
    constructor(private authService: UserService, private router: Router) { }
  
    canActivate(): Observable<boolean> {
        return this.authService.getCurrentUser().pipe(
          map((user) => {
            if (user.role === 'ADMIN') {
              return true;
            } else {
              this.router.navigate(['/home']);
              return false;
            }
          }),
          catchError((error) => {
            this.router.navigate(['/login']);
            return of(false); // Return false in case of an error
          })
        );
      }
      
  }