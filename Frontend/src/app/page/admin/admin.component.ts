import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/User.model';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  user?: User;

  constructor(private $userService: UserService) {}

  ngOnInit(): void {
    this.$userService.getCurrentUser().subscribe({
      next: (res: User | null) => {
        if (res) {
          this.user = res;
        }
      },
    });
  }
}
