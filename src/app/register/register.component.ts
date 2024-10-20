import { Component } from '@angular/core';
import {AuthService} from "../auth.service";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user = {
    name: '',
    email: '',
    password: '',
    acceptedTerms: false
  };

  constructor(private authService: AuthService,
              private toastr : ToastrService,
              private router : Router) {}

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
  onSubmit(registerForm: any) {
    if (registerForm.valid) {
      const data = {
        username : this.user.name,
        password : this.user.password,
        email : this.user.email
      }
      this.authService.register(data).subscribe(
        response => {
          this.toastr.success("Register successful! Please login to continue.", 'Successful!');
        },
        error => {
          this.toastr.error(error.error, 'Error!');
        }
      );
    }
  }
}
