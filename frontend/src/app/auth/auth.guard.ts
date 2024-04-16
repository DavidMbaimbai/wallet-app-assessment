import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  private url: any;
  constructor(private auth: AuthService, private router: Router) { }

  private authState(): boolean {
    if (this.isLoginOrRegister()) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
  private notAuthState(): boolean {
    if (this.isLoginOrRegister()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
  private isLoginOrRegister(): boolean {
    if (this.url.includes('/login')) {
      return true;
    }
    return false;
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
    this.url = state.url;
    if (this.auth) {
     return this.authState();
    }
    return this.notAuthState();
  }
}
