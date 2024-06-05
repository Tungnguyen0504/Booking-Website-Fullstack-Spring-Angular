import { Component, OnInit } from '@angular/core';
import { WebsocketService } from './service/websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  constructor(private $socketService: WebsocketService) {}

  ngOnInit(): void {
    this.$socketService.connect('/notification', (res) => {
      console.log(res);
    });
  }
}
