import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { PATH_V1 } from '../constant/Abstract.constant';
import { environment } from 'src/environments/environment';

const URL = environment.apiUrl + PATH_V1 + '/address';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  constructor(private $httpClient: HttpClient) {}

  getAllProvince(): Observable<any> {
    return this.$httpClient.get(URL + '/get-all-province');
  }

  getDistrictsByProvince(provinceId: number): Observable<any> {
    const params = new HttpParams().set('provinceId', provinceId.toString());
    return this.$httpClient.get(URL + '/get-districts-by-province', { params });
  }

  getWardsByDistrict(districtId: number): Observable<any> {
    const params = new HttpParams().set('districtId', districtId.toString());
    return this.$httpClient.get(URL + '/get-wards-by-district', { params });
  }
}
