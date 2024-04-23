import { Component, OnInit } from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomersService } from '../../shared/services/customers.service';
import { HeaderComponent } from '../../shared/partials/header/header.component'; // Import HeaderComponent

@Component({
  selector: 'app-debit-transaction',
  templateUrl: './debit-transaction.component.html',
  styleUrls: ['./debit-transaction.component.css'],
  standalone: true,
  imports: [
    HeaderComponent,
    ReactiveFormsModule
  ],
  providers: [HeaderComponent] // Add HeaderComponent to providers
})
export class DebitTransactionComponent implements OnInit {

  debitForm: any;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService,
    private route: ActivatedRoute,
    private customersService: CustomersService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.debitForm = this.fb.group({
      accountNumber: ['', Validators.required],
      amount: ['', Validators.required],
    });
  }

  isValidInput(fieldName: string | number): boolean {
    return this.debitForm.controls[fieldName].invalid &&
      (this.debitForm.controls[fieldName].dirty || this.debitForm.controls[fieldName].touched);
  }

  debitAccount(): void {
    if (this.debitForm.valid) {
      this.spinner.show();
      const formData = this.debitForm.value;
      this.customersService.debitAccount(formData).subscribe(
        (response: any) => {
          this.spinner.hide();
          this.toastr.success('Account debited successfully.');
          // Handle success, e.g., redirect to a success page
        },
        (error: any) => {
          this.spinner.hide();
          console.error('Error debiting account:', error);
          this.toastr.error('An error occurred while debiting the account. Please try again.');
        }
      );
    }
  }
}
