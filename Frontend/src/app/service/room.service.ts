import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  URL = environment.apiUrl + PATH_V1 + '/room';

  constructor(private httpClient: HttpClient) {}

  createNewRoom(data: any): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('accommodationId', data.accommodationId);
    formData.append('roomType', data.roomType);
    formData.append('roomArea', data.roomArea);
    formData.append('bed', data.bed);
    formData.append('capacity', data.capacity);
    formData.append('smoke', data.smoke);
    formData.append('quantity', data.quantity);
    formData.append('price', data.price);
    formData.append('discount', data.discount);
    for (let i = 0; i < data.dinningRooms.length; i++) {
      formData.append('dinningRooms', data.dinningRooms[i]);
    }
    for (let i = 0; i < data.bathRooms.length; i++) {
      formData.append('bathRooms', data.bathRooms[i]);
    }
    for (let i = 0; i < data.roomServices.length; i++) {
      formData.append('roomServices', data.roomServices[i]);
    }
    for (let i = 0; i < data.views.length; i++) {
      formData.append('views', data.views[i]);
    }
    for (let i = 0; i < data.images.length; i++) {
      formData.append('files', data.images[i]);
    }

    return this.httpClient.post(this.URL + '/save', formData);
  }

  getById(roomId: number) {
    return this.httpClient.get(`${this.URL}/get-by-id/${roomId}`);
  }

  getByAccommodationId(accommodationId: number) {
    return this.httpClient.get(`${this.URL}/get-by-accommodation-id/${accommodationId}`);
  }
}
