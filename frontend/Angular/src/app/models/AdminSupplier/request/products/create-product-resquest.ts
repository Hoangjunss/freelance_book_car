export class CreateProductRequest {
    name: string;
    price: number;
    description: string;
    productTypeId: number;
    supplierId: number;
    images: File[];
    officialPriceDTOS: {
        price: number;
        minQuantity: number;
        maxQuantity: number;
    }[];

   
}
