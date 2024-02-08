import { Injectable } from '@angular/core';
import { PATH_V1 } from '../constant/Abstract.constant';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  URL = environment.apiUrl + PATH_V1 + '/file';

  constructor(private httpClient: HttpClient) {}

  getImage(image: string) {
    const params = new HttpParams().set('filename', image);
    return this.httpClient.get(`${this.URL}/load`, { params });
  }

  getMultipleImages(imagePaths: string[]) {
    const body = {
      files: imagePaths,
    };
    return this.httpClient.post(`${this.URL}/load-multiple`, body);
  }
}
