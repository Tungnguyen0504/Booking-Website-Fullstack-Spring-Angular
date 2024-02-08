import { Component } from '@angular/core';
import { User } from './model/User.model';
import { AuthenticationService } from './service/authentication.service';
import { UserService } from './service/user.service';
import { AlertService } from './service/alert.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
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
    // this.getCurrentUser();
  }

  getCurrentUser() {
    if (this.isLogined) {
      this.$userService.getCurrentUser().subscribe({
        next: (response) => {
          this.user = response;
        },
        error: (error) => {
          this.isLogined = false;
          this.$alertService.error(error.error.message);
        },
      });
    }
  }
}
