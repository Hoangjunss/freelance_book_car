export class CreateTourRequest{
    name?: string;
    description?: string;
    startLocation?: string;
    endLocation?: string;
    isActive?: boolean;
    imageFirst?: File;
    imageSecond?: File;
    imageThird?: File;
}