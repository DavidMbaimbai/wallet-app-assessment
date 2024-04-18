import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  private apiUrl: string = environment.apiUrl + "customer/";

  constructor(public http: HttpClient) {
    console.log("Hello Customer Provider");
  }

  getCustomers(): any {
    return this.http.get(this.apiUrl);
  }

  getCustomer(id: number): any {
    return this.http.get(this.apiUrl + id);
  }

  createCustomer(data: any): any {
    return this.http.post(this.apiUrl, data);
  }

  // send account number to balance-enquiry get request
  getBalance(accountNumber: any): any {
    return this.http.get(this.apiUrl + "balance-enquiry/" + accountNumber);
  }

  transferAccount(data: any): any {
    return this.http.post(this.apiUrl + "transfer", data);
  }

  debitAccount(data: any): any {
    return this.http.post(this.apiUrl + "debit", data);
  }

  creditAccount(data: any): any {
    return this.http.post(this.apiUrl + "credit", data);
  }
}
