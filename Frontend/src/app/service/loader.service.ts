import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoaderService {
  private loading: boolean = false;

  constructor() {}

  setLoading(loading: boolean) {
    setTimeout(() => {
      this.loading = loading;
    });
  }

  getLoading(): boolean {
    return this.loading;
  }
}
