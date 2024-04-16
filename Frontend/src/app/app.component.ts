import { Component } from '@angular/core';
import { BaseApiService } from './service/base-api.service';
declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(public $baseApiService: BaseApiService) {
  }
}
