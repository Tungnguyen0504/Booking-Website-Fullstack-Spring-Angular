import { Component, Input } from '@angular/core';
import { Accommodation } from 'src/app/model/Accommodation.model';

@Component({
  selector: 'app-hotel-card',
  templateUrl: './hotel-card.component.html',
  styleUrls: ['./hotel-card.component.css'],
})
export class HotelCardComponent {
  @Input() accommodation?: Accommodation;
}
