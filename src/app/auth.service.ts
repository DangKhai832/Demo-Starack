import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/register`, userData, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/login`, credentials, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }).pipe(
      tap((response: any) => {
        const token = response?.token;
        if (token) {
          // Lưu token vào sessionStorage
          this.setToken(token);
          console.log("Token đã lưu vào sessionStorage:", token); // Thêm dòng này để kiểm tra

          // Thông báo với các tab khác rằng có một tab mới đăng nhập
          localStorage.setItem('authToken', token);

          // Xóa token khỏi localStorage ngay lập tức
          setTimeout(() => {
            localStorage.removeItem('authToken');
          }, 0);
        }
      })
    );
  }


  setToken(token: string): void {
    sessionStorage.setItem('authToken', token);  // Chỉ lưu vào sessionStorage
  }

  getToken(): string | null {
    return sessionStorage.getItem('authToken');  // Trả về token từ sessionStorage
  }

  logout(): void {
    sessionStorage.removeItem('authToken');  // Xóa token khi đăng xuất
  }

}
