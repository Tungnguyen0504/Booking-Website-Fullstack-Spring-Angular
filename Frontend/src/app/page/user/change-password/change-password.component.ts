import { Component } from '@angular/core';
import { AlertService } from 'src/app/service/alert.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent {
  constructor(private $alertService: AlertService, private $userService: UserService) {}

  sendEmail() {
    this.$userService.sendEmailChangePassword().subscribe({
      next: (res) => this.$alertService.success(res.message),
    });
  }
}
