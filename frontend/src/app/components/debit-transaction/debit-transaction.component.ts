import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../shared/partials/footer/footer.component';
import { HeaderComponent } from '../../shared/partials/header/header.component';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-debit-transaction',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, ReactiveFormsModule, NgxSpinnerModule],
  templateUrl: './debit-transaction.component.html',
  styleUrl: './debit-transaction.component.css'
})
export class DebitTransactionComponent implements OnInit {

  debitForm: any;
  errors: any = [];
  notify: any;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService,
    private route: ActivatedRoute) { }

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
    }

}
