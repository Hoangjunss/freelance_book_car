import { DeliveryUpdateDTOS } from "./DeliveryUpdateDTOS";

export class SupplyUpdateDTOS{
    image?: File;
    nameSupply?: string;
    address?: string;
    email?: string;
    password?: string;
    deliveryCreateDTOS: DeliveryUpdateDTOS[];
}