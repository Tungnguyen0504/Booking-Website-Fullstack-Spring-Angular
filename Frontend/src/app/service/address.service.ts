import { Injectable } from '@angular/core';
import { Axios } from 'axios';

@Injectable({
  providedIn: 'root',
})
export class AddressService {

  constructor(private $axios: Axios) { }

  get() {
    return this.$axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json");
  }
}
