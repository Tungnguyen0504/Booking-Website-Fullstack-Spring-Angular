import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Accommodation } from '../model/Accommodation.model';

@Injectable({
  providedIn: 'root',
})
export class RoomService {
  URL = environment.apiUrl + PATH_V1 + '/room';

  constructor(private httpClient: HttpClient) {}

  createNewRoom(files: FileList, data: any): Observable<any> {
    const formData: FormData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
    formData.append('accommodationId', data.accommodationId);
    formData.append('roomType', data.roomType);
    formData.append('roomArea', data.roomArea);
    formData.append('bed', data.bed);
    formData.append('capacity', data.capacity);
    formData.append('smoke', data.smoke);
    formData.append('quantity', data.quantity);
    formData.append('price', data.price);
    formData.append('discount', data.discount);
    formData.append('dinningRooms', data.dinningRooms);
    formData.append('bathRooms', data.bathRooms);
    formData.append('roomServices', data.roomServices);
    formData.append('views', data.views);

    return this.httpClient.post(this.URL + '/save', formData);
  }
}
