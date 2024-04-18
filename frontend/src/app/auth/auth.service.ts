import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from "../../environments/environment";
import { Customer } from '../shared/models/customer';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private uriseg = environment.apiUrl + 'customer';

  constructor(
    private http: HttpClient,
  ) {
    // get the user email from local storage
    const userEmail = localStorage.getItem('user_email');
    if (userEmail) {
      console.log('User Email: ', userEmail);
    } else {
      console.log('User Email not found');
    }
  }

  public register(userData: Customer): Observable<Customer> {
    const URI = this.uriseg + '/create';
    return this.http.post(URI, userData);
  }

  public loginUser(userData: any): Observable<any> {
    const URI = this.uriseg + '/login';
    return this.http.post(URI, userData).pipe(map(() => {

        localStorage.setItem('user_email', userData.email);

    }));
  }

  public logout(): void {
    localStorage.removeItem('user_email');
  }

  public isAuthenticated(): boolean {
    if (localStorage.getItem('user_email')) {
      return true;
    }
    return false;
  }

  public getUsername(): any {
    return localStorage.getItem('user_email');
  }

}
