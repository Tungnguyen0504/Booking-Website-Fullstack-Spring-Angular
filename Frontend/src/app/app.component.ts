import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
import { User } from './model/User.model';
import * as $ from 'jquery';
import 'owl.carousel';
import { AlertService } from './service/alert.service';

@Component({
  selector: 'app-user-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'Thepalatin.com';
  user?: User;
  isLogined: boolean = false;

  constructor(
    private authService: AuthenticationService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.isLogined = this.authService.isLoggedIn();
    this.getCurrentUser();
  }

  getCurrentUser() {
    if (this.isLogined) {
      this.authService.getCurrentUser().subscribe(
        (response) => {
          this.user = response;
          console.log(this.user);
        },
        (error) => {
          this.alertService.error(error.error.errorMessage);
        }
      );
    }
  }
}
