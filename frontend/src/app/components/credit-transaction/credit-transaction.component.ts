import { Component, OnInit } from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { CustomersService } from '../../shared/services/customers.service';
import {HeaderComponent} from "../../shared/partials/header/header.component"; // Import CustomersService

@Component({
  selector: 'app-credit-transaction',
  templateUrl: './credit-transaction.component.html',
  styleUrls: ['./credit-transaction.component.css'],
  imports: [
    HeaderComponent,
    ReactiveFormsModule
  ],
  standalone: true
})
export class CreditTransactionComponent implements OnInit {

  creditForm: any;
  errors: any = [];
  notify: any;

  constructor(
    private auth: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService,
    private route: ActivatedRoute,
    private customersService: CustomersService // Inject CustomersService
  ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.creditForm = this.fb.group({
      accountNumber: ['', Validators.required],
      amount: ['', Validators.required],
    });
  }

  isValidInput(fieldName: string | number): boolean {
    return this.creditForm.controls[fieldName].invalid &&
      (this.creditForm.controls[fieldName].dirty || this.creditForm.controls[fieldName].touched);
  }

  creditAccount(): void {
    if (this.creditForm.valid) {
      this.spinner.show();
      const formData = this.creditForm.value;
      this.customersService.creditAccount(formData).subscribe(
        (response: any) => {
          this.spinner.hide();
          this.toastr.success('Account credited successfully.');
          // Handle success, e.g., redirect to a success page
        },
        (error: any) => {
          this.spinner.hide();
          console.error('Error crediting account:', error);
          this.toastr.error('An error occurred while crediting the account. Please try again.');
        }
      );
    }
  }
}
