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
    email: '',
    password: ''
  };

  constructor(private authService: AuthService,
              private toastr: ToastrService,
              private router: Router) {}

  onSubmit(loginForm: any) {
    if (loginForm.valid) {
      const data = {
        email: this.user.email,
        password: this.user.password,
      };
      this.authService.login(data).subscribe(
        response => {
          // Lưu token vào session storage
          debugger
          this.authService.setToken(response.token);
          this.toastr.success("Login successful!", 'Successful!');
          this.router.navigate(['/manage/overview']);
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
