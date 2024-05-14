import { Address } from './Address.model';

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
  filePaths: string[];
  createdAt: string;
  modifiedAt: string;
}
