import { CUSTOM_ELEMENTS_SCHEMA, Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { FooterComponent } from '../../shared/partials/footer/footer.component';
import { HeaderComponent } from '../../shared/partials/header/header.component';
import { ToastrService } from 'ngx-toastr';
import { CustomersService } from '../../shared/services/customers.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-balance-enquiry',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, ReactiveFormsModule, NgxSpinnerModule],
  templateUrl: './balance-enquiry.component.html',
  styleUrl: './balance-enquiry.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
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
        this.errors.push(error.error.message);
        this.toastr.error('Balance Enquiry Failed');
        console.log('Error ', error);
      }
    );

  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }



}
