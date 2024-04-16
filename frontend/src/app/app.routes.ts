import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { TransactionHistoryComponent } from './components/transaction-history/transaction-history.component';
import { CreditTransactionComponent } from './components/credit-transaction/credit-transaction.component';
import { DebitTransactionComponent } from './components/debit-transaction/debit-transaction.component';
import { TransferTransactionComponent } from './components/transfer-transaction/transfer-transaction.component';

export const routes: Routes = [

  { path: '', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'trans-history', component: TransactionHistoryComponent },
  { path: 'trans-credit', component: CreditTransactionComponent },
  { path: 'trans-debit', component: DebitTransactionComponent },
];
