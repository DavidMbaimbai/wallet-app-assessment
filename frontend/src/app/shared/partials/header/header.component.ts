import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterLinkWithHref, RouterOutlet } from '@angular/router';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLinkWithHref, RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  constructor(public auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login'], {queryParams: {loggedOut: 'success'}});

  }

}
