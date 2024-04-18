import { Component } from '@angular/core';
import { FooterComponent } from '../../shared/partials/footer/footer.component';
import { HeaderComponent } from '../../shared/partials/header/header.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, FooterComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
