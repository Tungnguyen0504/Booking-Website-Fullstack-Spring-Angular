import { AccommodationType } from './AccommodationType.model';
import { Room } from './Room.model';

export interface Accommodation {
  accommodationId: number;
  accommodationName: string;
  accommodationType: AccommodationType;
  phone: string;
  email: string;
  star: number;
  description: string;
  checkin: string;
  checkout: string;
  status: string;
  fullAddress: string;
  createdAt: Date;
  modifiedAt: Date;
  specialArounds: string[];
  bathRooms: string[];
  bedRooms: string[];
  dinningRooms: string[];
  languages: string[];
  internets: string[];
  drinkAndFoods: string[];
  receptionServices: string[];
  cleaningServices: string[];
  pools: string[];
  others: string[];
  filePaths: string[];
  rooms: Room[];
}
