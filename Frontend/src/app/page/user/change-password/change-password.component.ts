import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/model/User.model';
import { AlertService } from 'src/app/service/alert.service';
import { UserService } from 'src/app/service/user.service';
import { FormVerificationDialogComponent } from 'src/app/shared/generic/form-verification-dialog/form-verification-dialog.component';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  user?: User;
  constructor(
    private dialog: MatDialog,
    private $alertService: AlertService,
    private $userService: UserService
  ) {}

  ngOnInit(): void {
    this.$userService.getCurrentUser().subscribe({
      next: (res) => (this.user = res!),
    });
  }

  sendEmail() {
    const dialogRef = this.dialog.open(FormVerificationDialogComponent, {
      data: {
        email: this.user?.email,
      },
      position: {
        top: '200px',
      },
      width: '576px',
      disableClose: true,
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result && result.isComplete) {
        this.$alertService.success('verified.');
      }
    });
  }
}
