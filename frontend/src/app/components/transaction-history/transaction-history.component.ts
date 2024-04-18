import { Component } from '@angular/core';
import { FooterComponent } from '../../shared/partials/footer/footer.component';
import { HeaderComponent } from '../../shared/partials/header/header.component';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [HeaderComponent, FooterComponent],
  templateUrl: './transaction-history.component.html',
  styleUrl: './transaction-history.component.css'
})
export class TransactionHistoryComponent {

}
