import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { User } from 'src/app/model/User.model';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.css'],
})
export class UserHeaderComponent implements OnInit {
  @Input() drawer!: MatDrawer;
  
  user?: User;

  constructor(
    private $userService: UserService,
    private $authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    if(this.$authenticationService.isLoggedIn()) {
      this.$userService.getCurrentUser().subscribe({
        next: (response) => {
          this.user = response;
        },
      });
    }
  }

  test() {
    this.drawer.toggle();
  }
}
