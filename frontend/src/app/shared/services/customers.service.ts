import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  private apiUrl: string = environment.apiUrl + 'customer/';

  constructor(public http: HttpClient) {}

  getCustomers(): any {
    return this.http.get(this.apiUrl);
  }

  getCustomer(id: number): any {
    return this.http.get(this.apiUrl + id);
  }

  createCustomer(data: any): any {
    return this.http.post(this.apiUrl, data);
  }

  getBalance(accountNumber: any): any {
    return this.http.get(this.apiUrl + 'balance-enquiry/' + accountNumber);
  }

  debitAccount(data: any): any {
    return this.http.post(this.apiUrl + 'debit', data);
  }

  creditAccount(data: any): any {
    return this.http.post(this.apiUrl + 'credit', data);
  }
}
