import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../shared/partials/footer/footer.component';
import { HeaderComponent } from '../../shared/partials/header/header.component';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-credit-transaction',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, ReactiveFormsModule, NgxSpinnerModule],
  templateUrl: './credit-transaction.component.html',
  styleUrl: './credit-transaction.component.css'
})
export class CreditTransactionComponent implements OnInit {

  creditForm: any;
  errors: any = [];
  notify: any;

  constructor(private auth: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private spinner: NgxSpinnerService,
    private route: ActivatedRoute) { }

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
    }

}
