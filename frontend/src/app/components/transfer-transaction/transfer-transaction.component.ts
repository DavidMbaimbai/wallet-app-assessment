import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../shared/partials/footer/footer.component';
import { HeaderComponent } from '../../shared/partials/header/header.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-transfer-transaction',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, ReactiveFormsModule, NgxSpinnerModule],
  templateUrl: './transfer-transaction.component.html',
  styleUrl: './transfer-transaction.component.css'
})
export class TransferTransactionComponent implements OnInit {

  transferForm: any;
  errors: any = [];
  notify: any;

  constructor(
    private fb: FormBuilder,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService,
    private route: ActivatedRoute) { }

    ngOnInit(): void {
      this.initForm();
    }

    initForm(): void {
      this.transferForm = this.fb.group({
        sourceAccountNumber: ['', Validators.required],
        destinationAccountNumber: ['', Validators.required],
        amount: ['', Validators.required],
      });
    }

    isValidInput(fieldName: string | number): boolean {
      return this.transferForm.controls[fieldName].invalid &&
        (this.transferForm.controls[fieldName].dirty || this.transferForm.controls[fieldName].touched);
    }

    transferAccount(): void {
    }

}
