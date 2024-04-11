import { AfterViewInit, Component } from '@angular/core';
import { User } from './model/User.model';
import { AuthenticationService } from './service/authentication.service';
import { UserService } from './service/user.service';
import { AlertService } from './service/alert.service';
declare var $: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {}
