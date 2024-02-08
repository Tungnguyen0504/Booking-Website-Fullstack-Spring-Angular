import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-city-block',
  templateUrl: './city-block.component.html',
  styleUrls: ['./city-block.component.css']
})
export class CityBlockComponent implements OnInit {
  @Input() classHeader?: string;
  @Input() name?: string;
  @Input() image?: string;

  ngOnInit(): void {
  }
}
