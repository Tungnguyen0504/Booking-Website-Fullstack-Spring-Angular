import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './service/authentication.service';
import { User } from './model/User.model';
import { AlertService } from './service/alert.service';
import 'owl.carousel';
import { UserService } from './service/user.service';

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
    private $authService: AuthenticationService,
    private $userService: UserService,
    private $alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.isLogined = this.$authService.isLoggedIn();
    this.getCurrentUser();
  }

  getCurrentUser() {
    if (this.isLogined) {
      this.$userService.getCurrentUser().subscribe({
        next: (response) => {
          this.user = response;
        },
        error: (error) => {
          this.isLogined = false;
          this.$alertService.error(error.error.errorMessage);
        },
      });
    }
  }
}
