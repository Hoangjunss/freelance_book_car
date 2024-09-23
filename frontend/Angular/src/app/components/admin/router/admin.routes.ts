import { Routes } from "@angular/router";
import { LayoutComponent } from "../layout/layout.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { TourComponent } from "../tour-manager/tour/tour.component";
import { TourScheduleComponent } from "../tour-manager/tour-schedule/tour-schedule.component";
import { TourStatisticsComponent } from "../tour-manager/tour-statistics/tour-statistics.component";

export const adminRoutes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children:[
            {
                path: 'dashboard',
                component: DashboardComponent
            },
            {
                path: 'tour-manager',
                children: [
                    {
                        path: 'tour',
                        component: TourComponent
                    },
                    {
                        path: 'tour-schedule',
                        component: TourScheduleComponent
                    },
                    {
                        path: 'tour-statistics',
                        component: TourStatisticsComponent
                    }
                ]
            }
        ]
    }
]