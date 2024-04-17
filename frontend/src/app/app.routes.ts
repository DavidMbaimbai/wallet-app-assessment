import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { TransactionHistoryComponent } from './components/transaction-history/transaction-history.component';
import { CreditTransactionComponent } from './components/credit-transaction/credit-transaction.component';
import { DebitTransactionComponent } from './components/debit-transaction/debit-transaction.component';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [

  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'trans-history', component: TransactionHistoryComponent, canActivate: [AuthGuard]},
  { path: 'trans-credit', component: CreditTransactionComponent, canActivate: [AuthGuard]},
  { path: 'trans-debit', component: DebitTransactionComponent, canActivate: [AuthGuard]},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},


];
