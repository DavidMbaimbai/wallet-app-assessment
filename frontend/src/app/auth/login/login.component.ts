import { CUSTOM_ELEMENTS_SCHEMA, Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLinkWithHref } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, NgxSpinnerModule, RouterLinkWithHref],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class LoginComponent implements OnInit {

  loginForm: any;
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
        const key1 = 'registered';
        const key2 = 'loggedOut';
      if (params[key1] === 'success') {
        this.notify = 'You have been successfully registered. Please Log in';
        this.toastr.success(this.notify);
      }
      if (params[key2] === 'success') {
        this.notify = 'You have been logged out successfully';
        this.toastr.success(this.notify);
      }
      });
    }

    initForm(): void {
      this.loginForm = this.fb.group({
        email: ['', [Validators.required,
        Validators.pattern('^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$')]],
        password: ['', Validators.required]
      });
    }

    isValidInput(fieldName: string | number): boolean {
      return this.loginForm.controls[fieldName].invalid &&
        (this.loginForm.controls[fieldName].dirty || this.loginForm.controls[fieldName].touched);
    }

    login(): void {
      this.errors = [];
      this.spinner.show();
      this.auth.loginUser(this.loginForm.value)
      .subscribe({
        next: () => {
          this.router.navigate(['/'], { queryParams: { loggedin: 'success' } });
          this.spinner.hide();
        },
        error: (errorResponse) => {
          this.errors.push("Login Failed. Try again");
          console.log(errorResponse);
          this.spinner.hide();
        },
      });
    }


}
