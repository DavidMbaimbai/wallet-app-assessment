import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {

  private apiUrl: string = environment.apiUrl + "transaction/";

  constructor(public http: HttpClient) {
    console.log("Hello Transaction Provider");
  }

  getTransactionHistory(): any {
    return this.http.get(this.apiUrl + "history");
  }
}
