import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "./auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private router: Router,private authService : AuthService) {}

  ngOnInit() {
    // Lắng nghe sự kiện thay đổi storage
    window.addEventListener('storage', (event) => {
      if (event.key === 'authToken' && !event.newValue) {
        // Khi token bị xóa từ tab khác, thực hiện đăng xuất
        this.authService.logout();
        this.router.navigate(['/login']);
      }
    });
  }
}
