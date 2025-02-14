import { Address } from './Address.model';
import { FileDto } from './FileDto.model';

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  address: Address;
  phoneNumber: string;
  dateOfBirth: string;
  status: string;
  role: string;
  files: FileDto[];
  createdTime: string;
  updatedTime: string;
}
