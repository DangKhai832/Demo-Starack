import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrl: './manage.component.css'
})
export class ManageComponent {
  currentUrl: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Lấy URL hiện tại
    this.router.events.subscribe(() => {
      this.currentUrl = this.router.url;
    });
  }

  isUserManagementPage() {
    return this.currentUrl.includes('/manage/user-management');
  }

  isOverviewPage() {
    return this.currentUrl.includes('/manage/overview');
  }
}
