import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Accommodation } from '../model/Accommodation.model';
import { BasePagingRequest } from '../model/request/BasePagingRequest.model';
import { BasePagingResponse } from '../model/response/BasePagingRequest.model';

@Injectable({
  providedIn: 'root',
})
export class AccommodationService {
  URL = environment.apiUrl + PATH_V1 + '/accommodation';

  constructor(private httpClient: HttpClient) {}

  createNewAccommodation(data: any): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('accommodationName', data.accommodationName);
    formData.append('accommodationTypeId', data.accommodationType);
    formData.append('phone', data.phone);
    formData.append('email', data.email);
    formData.append('star', data.star);
    formData.append('description', data.description);
    formData.append('checkin', data.checkin);
    formData.append('checkout', data.checkout);
    formData.append('wardId', data.wardId);
    formData.append('specificAddress', data.specificAddress);
    for (let i = 0; i < data.specialArounds.length; i++) {
      formData.append('specialArounds', data.specialArounds[i]);
    }
    for (let i = 0; i < data.bathRooms.length; i++) {
      formData.append('bathRooms', data.bathRooms[i]);
    }
    for (let i = 0; i < data.bedRooms.length; i++) {
      formData.append('bedRooms', data.bedRooms[i]);
    }
    for (let i = 0; i < data.dinningRooms.length; i++) {
      formData.append('dinningRooms', data.dinningRooms[i]);
    }
    for (let i = 0; i < data.languages.length; i++) {
      formData.append('languages', data.languages[i]);
    }
    for (let i = 0; i < data.internets.length; i++) {
      formData.append('internets', data.internets[i]);
    }
    for (let i = 0; i < data.drinkAndFoods.length; i++) {
      formData.append('drinkAndFoods', data.drinkAndFoods[i]);
    }
    for (let i = 0; i < data.receptionServices.length; i++) {
      formData.append('receptionServices', data.receptionServices[i]);
    }
    for (let i = 0; i < data.cleaningServices.length; i++) {
      formData.append('cleaningServices', data.cleaningServices[i]);
    }
    for (let i = 0; i < data.pools.length; i++) {
      formData.append('pools', data.pools[i]);
    }
    for (let i = 0; i < data.others.length; i++) {
      formData.append('others', data.others[i]);
    }
    for (let i = 0; i < data.images.length; i++) {
      formData.append('files', data.images[i]);
    }

    return this.httpClient.post(this.URL + '/save', formData);
  }

  getAllAccommodation(): Observable<Accommodation[]> {
    return this.httpClient.get<Accommodation[]>(`${this.URL}/get-all`);
  }

  getAccommodations(request: BasePagingRequest): Observable<BasePagingResponse> {
    return this.httpClient.post<BasePagingResponse>(`${this.URL}/get-accommodations`, request);
  }

  getById(id: number): Observable<Accommodation> {
    return this.httpClient.get<Accommodation>(`${this.URL}/get-by-id/${id}`);
  }
}
