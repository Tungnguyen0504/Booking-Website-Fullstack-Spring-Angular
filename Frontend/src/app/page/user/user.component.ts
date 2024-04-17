import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { BaseApiService } from 'src/app/service/base-api.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  constructor(public $baseApiService: BaseApiService, public $authService: AuthenticationService) {}
}
