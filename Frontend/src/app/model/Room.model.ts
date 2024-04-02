export interface Room {
  roomId: number;
  accommodationId: number;
  roomType: string;
  roomArea: number;
  bed: string;
  capacity: number;
  smoke: boolean;
  price: number;
  discountPercent: number;
  quantity: number;
  status: string;
  views: string[];
  dinningRooms: string[];
  bathRooms: string[];
  roomServices: string[];
  filePaths: string[];
}
