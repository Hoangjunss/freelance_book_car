import { OfficialPriceDTOS } from "./OfficialPriceDTOS";

export class ProductCreateDTOS{
    nameProduct?: string;
    price?: number;
    description?: string;
    productTypeId?: number;
    supplierId?: number;
    images?: File;
    officialPriceDTOS?: OfficialPriceDTOS[];
}