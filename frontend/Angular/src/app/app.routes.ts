import { Routes } from '@angular/router';
import { LocationListComponent } from './components/user/location-list/location-list.component';
import { NavbarComponent } from './components/user/navbar/navbar.component';
import { LoginComponent } from './components/auth/login/login.component';
import { LocationDetailComponent } from './components/user/location-detail/location-detail.component';
import { BookingComponent } from './components/user/booking/booking.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { PaymentComponent } from './components/user/payment/payment.component';
import { HomeComponent } from './components/user/home/home.component';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },
  {
    path: 'location-list/:location',
    component: LocationListComponent
  },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      }
    ]
  },
  {
    path: 'location-detail',
    component: LocationDetailComponent
  },
  { path: 'location-details/:id',
    component: LocationDetailComponent 
  },
  {
    path: 'admin',
    loadChildren: () => import('./components/admin/router/admin.modules').then(m => m.AdminModule)
  },
  {
    path: 'booking',
    component: BookingComponent
  },
  {
    path: 'payment',
    component: PaymentComponent

  },
  {
    path: 'navbar',
    component: NavbarComponent
  },
  {
    path: '**',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  }
 
];
