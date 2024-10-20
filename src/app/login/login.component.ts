import { Component } from '@angular/core';
import { AuthService } from "../auth.service";
import { ToastrService } from "ngx-toastr";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user = {
    username: '',
    password: ''
  };

  constructor(private authService: AuthService,
              private toastr: ToastrService,
              private router: Router) {}

  onSubmit(loginForm: any) {
    if (loginForm.valid) {
      this.authService.login(this.user).subscribe(
        response => {
          this.toastr.success("Login successful!", 'Successful!');
          // Điều hướng đến trang khác nếu cần
        },
        error => {
          this.toastr.error(error.error, 'Error!');
        }
      );
    }
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }
}
