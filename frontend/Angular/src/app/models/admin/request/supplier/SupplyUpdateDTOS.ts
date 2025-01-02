import { DeliveryUpdateDTOS } from "./DeliveryUpdateDTOS";

export class SupplyUpdateDTOS{
    id?: number;
    image?: File;
    nameSupply?: string;
    address?: string;
    email?: string;
    phone?: string;
    deliveryCreateDTOS: DeliveryUpdateDTOS[];
}