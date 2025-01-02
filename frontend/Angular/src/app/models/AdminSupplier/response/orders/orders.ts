export class OrderDTO{
    id?: number;
    user?:{
        userId?: number;
        fullname?: string;
        phone?: string;
        email?: string;
    };
    orderStatus?: string;
    quantity?: number;
    totalPrice?: number;
    createDate?: Date;
    fullname?: string;
    address?: string;
    phone?: string;
    orderDetailSDTO?:{
        id?: number;
        quantity?: number;
        nameProduct?: string;
        price?: number;
        image?: string;
    }[];
}