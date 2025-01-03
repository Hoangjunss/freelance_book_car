import { Routes } from "@angular/router";
import { LayoutComponent } from "../layout/layout.component";

export const supplierRouter: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            {

            }
        ]
    }
];