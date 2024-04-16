import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private uriseg = environment.apiUrl + 'users';

  private getToken: any;

  constructor(private http: HttpClient) {
  }

  public register(userData: any): Observable<any> {
    const URI = this.uriseg + '/register';
    return this.http.post(URI, userData);
  }

  public loginUser(userData: any): Observable<any> {
    const URI = this.uriseg + '/login';
    return this.http.post(URI, userData).pipe(map(token => {

      // console.log(Object.values(token))
      // for (const [key, value] of Object.entries(token)) {

      //   console.log(`${key}: ${value}`);
      // }
      // console.log('Token ndi: ', userData.email);
      // return this.saveToken(token);

      // Get token from API endpoint
      const arrOfObj = Object.entries(token).map(entry => entry[1]);
      const tokenValue = arrOfObj;
      const getVal = tokenValue[3];

    }));
  }

  public logout(): void {

  }

  public isAuthenticated() {
  }

  public getUsername(): any {
  }

}
