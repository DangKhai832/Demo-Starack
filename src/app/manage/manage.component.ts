import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css'] // Chú ý: styleUrls cần có chữ 's'
})
export class ManageComponent implements OnInit {
  currentUrl: string = '';
  content: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Lấy URL hiện tại ngay trong ngOnInit
    this.currentUrl = this.router.url;

    // Xác định nội dung dựa trên URL
    if (this.isUserManagementPage()) {
      this.content = 'User Management';
    } else if (this.isOverviewPage()) {
      this.content = 'Overview';
    }

    // Hoặc nếu cần theo dõi sự thay đổi URL
    this.router.events.subscribe(() => {
      this.currentUrl = this.router.url;
      if (this.isUserManagementPage()) {
        this.content = 'User Management';
      } else if (this.isOverviewPage()) {
        this.content = 'Overview';
      }
    });
  }

  isUserManagementPage(): boolean {
    return this.currentUrl.includes('/manage/user-management');
  }

  isOverviewPage(): boolean {
    return this.currentUrl.includes('/manage/overview');
  }

  getContent(content: string): void {
    this.content = content;
  }
}
