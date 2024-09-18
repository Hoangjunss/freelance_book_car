import { Routes } from '@angular/router';
import { LocationListComponent } from './location-list/location-list.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LocationDetailComponent } from './location-detail/location-detail.component';


export const routes: Routes = [ 

    {
        path: 'location-list/:location',
        component: LocationListComponent
    },
    {
        path: 'home',
        component: NavbarComponent
    },
    { 
        path: '',
        component: LoginComponent 
    },
    {
        path:'location-detail',
        component: LocationDetailComponent
    }
      
];
