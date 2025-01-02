import { DeliveryCreateDTOS } from "./DeliveryCreateDTOS";

export class SupplyCreateDTOS{
    image?: File;
    nameSupply?: string;
    address?: string;
    email?: string;
    password?: string;
    deliveryCreateDTOS: DeliveryCreateDTOS[];
}