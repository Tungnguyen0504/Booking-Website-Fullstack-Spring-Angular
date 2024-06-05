import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-user-tab',
  templateUrl: './user-tab.component.html',
  styleUrls: ['./user-tab.component.css'],
})
export class UserTabListComponent {
  @Input() tabActive: number = 0;

  tabList: any = [
    {
      icon: 'fa-solid fa-user',
      title: 'Thông tin cá nhân',
      link: 'user-information',
    },
    {
      icon: 'fa-solid fa-lock',
      title: 'Đổi mật khẩu',
      link: 'change-password',
    },
    {
      icon: 'fa-solid fa-clock',
      title: 'Lịch sử đặt phòng',
      link: 'booking-history',
    },
  ];
}
