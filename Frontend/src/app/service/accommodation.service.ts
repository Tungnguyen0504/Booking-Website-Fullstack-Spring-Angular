import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AccommodationService {
  URL = environment.apiUrl + PATH_V1 + '/accommodation';

  constructor(private httpClient: HttpClient) {}

  createNewAccommodation(files: FileList, data: any): Observable<any> {
    const formData: FormData = new FormData();
    for (let i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
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
    formData.append('specialArounds', data.specialArounds);

    return this.httpClient.post(this.URL + '/save', formData);
  }
}
