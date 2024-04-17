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

  // balance enquiry
  getBalance(data: any): any {
    return this.http.get(this.apiUrl + "balance-enquiry", data);
  }


  debitAccount(data: any): any {
    return this.http.post(this.apiUrl + "debit", data);
  }

  creditAccount(data: any): any {
    return this.http.post(this.apiUrl + "credit", data);
  }
}
