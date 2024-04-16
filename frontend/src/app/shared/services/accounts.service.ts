import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  private apiUrl: string = environment.apiUrl + "account/";


  constructor(public http: HttpClient) {
    console.log("Hello Accounts Provider");
  }
}
