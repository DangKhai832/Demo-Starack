import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../auth.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ManageService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getDataUserManagement(fullName: string | null, page: number = 0, size: number = 10): Observable<{ users: any[], totalItems: number }> {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    const requestDto = {
      fullName: fullName || null,
      page: page,
      size: size
    };

    return this.http.post<{ users: any[], totalItems: number }>(`${this.apiUrl}/users/search`, requestDto, { headers });
  }

  getUserById(userId: string): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.apiUrl}/users/${userId}`, { headers });
  }
  getTrackLogById(userId: string): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.apiUrl}/tracklogs/${userId}`, { headers });
  }

  createUser(newMember: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(`${this.apiUrl}/users`, newMember, { headers });
  }

  updateUser(userId: string, updatedUser: any): Observable<any> {
    const token = sessionStorage.getItem('authToken');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.put<any>(`${this.apiUrl}/users/${userId}`, updatedUser, { headers });
  }

  deleteUser(id: string) {
    const token = sessionStorage.getItem('authToken');
    debugger
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(`${this.apiUrl}/users/${id}`, { headers }); // Thêm dấu '/'
  }

}
