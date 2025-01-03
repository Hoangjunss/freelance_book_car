import { Routes } from "@angular/router";
import { LayoutComponent } from "../layout/layout.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { TourComponent } from "../tour-manager/tour/tour.component";
import { TourScheduleComponent } from "../tour-manager/tour-schedule/tour-schedule.component";
import { TourStatisticsComponent } from "../tour-manager/tour-statistics/tour-statistics.component";
import { TourismComponent } from "../tourism-manager/tourism/tourism.component";
import { TicketComponent } from "../tourism-manager/ticket/ticket.component";
import { TourismStatisticsComponent } from "../tourism-manager/tourism-statistics/tourism-statistics.component";
import { HotelComponent } from "../hotel-manager/hotel/hotel.component";
import { HotelBookingComponent } from "../hotel-manager/hotel-booking/hotel-booking.component";
import { HotelStatisticsComponent } from "../hotel-manager/hotel-statistics/hotel-statistics.component";
import { PromotionComponent } from "../promotion-manager/promotion/promotion.component";
import { VoucherComponent } from "../promotion-manager/voucher/voucher.component";

import { PageHomeComponent } from "../page/page-home/page-home.component";
import { BookingPendingComponent } from "../booking/booking-pending/booking-pending.component";
import { BookingAcceptComponent } from "../booking/booking-accept/booking-accept.component";
import { BookingCancelComponent } from "../booking/booking-cancel/booking-cancel.component";
import { ProductActiveComponent } from "../products-active/products.component";
import { ProductPendingComponent } from "../products-pending/products.component";
import { OrdersPendingComponent } from "../orders-pending/orders.component";
import { OrdersAccessComponent } from "../orders-access/orders.component";
import { LienheComponent } from "../lienhe/lienhe.component";
import { SupportComponent } from "../support/support.component";
import { ThanhvienComponent } from "../thanhvien/thanhvien.component";
import { ProductCreateComponent } from "../product-create/product-create.component";
import { ProductUpdateComponent } from "../product-update/product-update.component";
import { LienheAdminComponent } from "../admin/lienhe/lienhe.component";
import { SupportAdminComponent } from "../admin/support/support.component";
import { ThanhvienAdminComponent } from "../admin/thanhvien/thanhvien.component";
import { ProductActiveAdminComponent } from "../admin/products-active/products.component";
import { ProductPendingAdminComponent } from "../admin/products-pending/products.component";
import { UserAdminComponent } from "../admin/user/user.component";
import { SupplierAdminComponent } from "../admin/supplier/supplier.component";

export const adminRoutes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children:[
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
            },
            {
                path: 'tourism-manager',
                children:[
                    {
                        path: 'tourism',
                        component: TourismComponent
                    },
                    {
                        path: 'ticket',
                        component: TicketComponent
                    },
                    {
                        path: 'tourism-statistics',
                        component: TourismStatisticsComponent
                    }
                ]
            },
            {
                path: 'hotel-manager',
                children:[
                    {
                        path: 'hotel',
                        component: HotelComponent
                    },
                    {
                        path: 'hotel-booking',
                        component: HotelBookingComponent
                    },
                    {
                        path: 'hotel-statistics',
                        component: HotelStatisticsComponent
                    }
                ]
            },
            {
                path: 'promotion-manager',
                children:[
                    {
                        path: 'promotion',
                        component: PromotionComponent
                    },
                    {
                        path: 'voucher',
                        component: VoucherComponent
                    }
                ]
            },
           
            {
                path: 'page-manager',
                children: [
                    {
                        path: 'home',
                        component: PageHomeComponent
                    }
                ]
            },
            {
                path: 'booking',
                children: [
                    {
                        path: 'pending',
                        component: BookingPendingComponent
                    },
                    {
                        path: 'accept',
                        component: BookingAcceptComponent
                    },
                    {
                        path: 'cancel',
                        component: BookingCancelComponent
                    }
                ]
            },
            {
                path: 'product',
                children: [
                    {
                        path: 'productActive',
                        component: ProductActiveComponent
                    },
                    {
                        path: 'productPending',
                        component: ProductPendingComponent
                    },
                    {
                        path: 'create',
                        component: ProductCreateComponent
                    },
                    {
                        path: 'update',
                        component: ProductUpdateComponent
                    }
                   
                ]
              
            },
            {
                path: 'orders',
                children: [
                    {
                        path: 'ordersAccess',
                        component: OrdersAccessComponent
                    },
                    {
                        path: 'ordersPending',
                        component: OrdersPendingComponent
                    },
                   
                ]
            },
            {
                path: 'lienhe',
                component: LienheComponent
               
            },
            {
                path: 'support',
                component: SupportComponent
            },
            {
                path: 'thanhvien',
               component:ThanhvienComponent
            },
            {
                path: 'admin',
                children: [
                    {
                        path: 'product',
                children: [
                    {
                        path: 'productAccess',
                        component: ProductActiveAdminComponent
                    },
                    {
                        path: 'productPending',
                        component: ProductPendingAdminComponent
                    },
                   
                ]
                    },
                    {
                        path: 'lienhe',
                        component: LienheAdminComponent
                       
                    },
                    {
                        path: 'support',
                        component: SupportAdminComponent
                    },
                    {
                        path: 'thanhvien',
                       component:ThanhvienAdminComponent
                    },
                    {
                        path: 'user',
                       component:UserAdminComponent
                    },
                    {
                        path: 'supplier',
                       component:SupplierAdminComponent
                    },
                   
                ]
            }
        ]
    }
];