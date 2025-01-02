export class Product {
    id: number;
    nameProduct: string;
    price: number;
    description: string;
    nameProductType: string;
    address: string;
    nameSupplier: string;
    listImage: {
        id: number;
        url: string;
    }[];
    officialPriceDTO: {
        id: number;
        minQuantity: number;
        maxQuantity: number;
        price: number;
    }[];
    productStatusActivity: string;
    productStatusVerify: string;

   
}
