export class UpdateTourRequest{
    id?: number;
    name?: string;
    description?: string;
    startLocation?: string;
    endLocation?: string;
    isActive?: boolean;
    imageFirst?: File;
    imageSecond?: File;
    imageThird?: File;
}