import { Component } from '@angular/core';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent {
  tabList: any[] = [
    {
      icon: 'fa-solid fa-user',
      title: 'Thông tin cá nhân',
    },
    {
      icon: 'fa-solid fa-lock',
      title: 'Đổi mật khẩu',
    },
    {
      icon: 'fa-solid fa-clock',
      title: 'Lịch sử đặt phòng',
    },
  ];
  selectedTabChange($event: any) {}
}
