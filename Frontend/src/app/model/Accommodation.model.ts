import { AccommodationType } from './AccommodationType.model';

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
  fullAddress: string;
  specialArounds: string[];
  filePaths: string[];
}
