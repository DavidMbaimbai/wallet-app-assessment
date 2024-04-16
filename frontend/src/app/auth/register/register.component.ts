import { CUSTOM_ELEMENTS_SCHEMA, Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { Router } from '@angular/router';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgxSpinnerModule, RouterLinkWithHref],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RegisterComponent implements OnInit {

  registerForm: any;
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
      this.route.queryParams.subscribe((params) => {
        const key1 = 'loggedOut';
        if (params[key1] === 'success') {
          this.notify = 'You have been logged out successfully';
          this.toastr.success(this.notify);
        }
      });
    }

    initForm(): void {
      this.registerForm = this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        otherName: ['', Validators.required],
        gender: ['', Validators.required],
        address: ['', Validators.required],
        stateOfOrigin: ['', Validators.required],
        phoneNumber: ['', Validators.required],
        alternativeNumber: ['', Validators.required],
        email: ['', [Validators.required,
        Validators.pattern('^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$')]],
        password: ['', Validators.required]
      });
    }

    isValidInput(fieldName: string | number): boolean {
      return this.registerForm.controls[fieldName].invalid &&
        (this.registerForm.controls[fieldName].dirty || this.registerForm.controls[fieldName].touched);
    }

    register(): void {
      this.errors = [];
      this.spinner.show();
      this.auth.register(this.registerForm.value)
      .subscribe(() => {
        this.spinner.hide();
        this.router.navigate(['/'], { queryParams: { loggedin: 'success' } });
      }, (error) => {
        this.spinner.hide();
        this.errors = error.error;
      });
    }

}
