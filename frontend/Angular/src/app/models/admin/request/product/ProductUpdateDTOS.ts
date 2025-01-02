import { Image } from "./Image";
import { OfficialPriceDTOS } from "./OfficialPriceDTOS";

export class ProductUpdateDTOS{
    name?: string;
    id?: number;
    price?: number;
    description?: string;
    image?: Image[];
    officialPriceDTOS?: OfficialPriceDTOS[];
}