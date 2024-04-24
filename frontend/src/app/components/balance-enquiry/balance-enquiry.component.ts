import { Component, OnDestroy, OnInit } from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgxSpinnerModule, NgxSpinnerService} from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CustomersService } from '../../shared/services/customers.service';
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import {HeaderComponent} from "../../shared/partials/header/header.component";

@Component({
  selector: 'app-balance-enquiry',
  templateUrl: './balance-enquiry.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    HeaderComponent,
    NgxSpinnerModule
  ],
  styleUrls: ['./balance-enquiry.component.css']
})
export class BalanceEnquiryComponent implements OnInit, OnDestroy {

  balanceForm: any;
  errors: any = [];
  notify: any;
  isSubmitted = false;
  balance: any;
  private subscription?: Subscription;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomersService,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService,
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.balanceForm = this.fb.group({
      accountNumber: ['', Validators.required],
    });
  }

  isValidInput(fieldName: string | number): boolean {
    return this.balanceForm.controls[fieldName].invalid &&
      (this.balanceForm.controls[fieldName].dirty || this.balanceForm.controls[fieldName].touched);
  }

  getBalance(): void {
    this.spinner.show();
    this.isSubmitted = true;
    this.errors = [];
    this.subscription = this.customerService.getBalance(this.balanceForm.value.accountNumber).subscribe(
      (response: any) => {
        this.spinner.hide();
        this.balance = response.accountInfo;
        this.toastr.success('Balance Enquiry Successful');
        console.log('Success ', response);
      },
      (error: any) => {
        this.spinner.hide();
        if (error instanceof HttpErrorResponse && error.error && error.error.message) {
          this.errors.push(error.error.message);
        } else if (error.status === 403) {
          this.errors.push("Unauthorized access. Please check your credentials.");
        } else {
          this.errors.push("An unknown error occurred.");
        }
        this.toastr.error('Balance Enquiry Failed');
        console.log('Error ', error);
      }
    );
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
