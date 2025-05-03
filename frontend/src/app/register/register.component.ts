import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatCardModule, MatCardTitle } from '@angular/material/card';
import { MatError, MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInput, MatInputModule } from '@angular/material/input';
import { HttpClient } from '@angular/common/http';
import { MyErrorStateMatcher } from '../overview/overview.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
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
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {

  router = inject(Router);
  httpClient = inject(HttpClient);
  usernameControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();

  register() {
    this.httpClient
      .post('auth/register', {
        name: this.usernameControl.value,
        password: this.passwordControl.value,
      })
      .subscribe(_ => {
        localStorage.setItem(
          'auth',
          btoa(this.usernameControl.value + ':' + this.passwordControl.value),
        );
        this.router.navigate(['/']);
      });
  }

  onKeyDown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.register();
    }
  }
}
