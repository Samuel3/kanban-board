import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatButton, MatButtonModule } from '@angular/material/button';
import {
  MatError,
  MatFormField,
  MatFormFieldModule,
} from '@angular/material/form-field';
import { MatInput, MatInputModule } from '@angular/material/input';
import {
  FormControl,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MyErrorStateMatcher } from '../overview/overview.component';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    CommonModule,
    MatCardTitle,
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    ReactiveFormsModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  usernameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  httpClient = inject(HttpClient);
  router = inject(Router);
  loginError = false;

  onKeyDown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.login();
    }
  }

  login() {
    const authentication = btoa(
      this.usernameControl.value + ':' + this.passwordControl.value,
    );
    this.httpClient
      .get('auth/login', { headers: { "Authorization": `Basic ${authentication}` } })
      .subscribe({
        complete: () => {
          localStorage.setItem('auth', authentication);
          this.loginError = false;
          this.router.navigate(['/']);
        },
        error: _ => {
          this.loginError = true;
        }
      });
  }

  register() {
    this.router.navigate(['/register']);
  }

  protected readonly console = console;
}
