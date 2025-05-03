import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import {
  FormControl,
  FormGroupDirective,
  FormsModule,
  NgForm,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClient } from '@angular/common/http';
import { BoardDefinition } from './overview.types';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../deletion-dialog/deletion-dialog.component';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null,
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
  ],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css',
})
export class OverviewComponent {
  boards: BoardDefinition[] = [];
  showDialog = false;
  boardNameFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  httpClient = inject(HttpClient);
  dialog = inject(MatDialog);

  constructor(private router: Router) {
    if (!localStorage.getItem('auth')) {
      this.router.navigate(['/login']);
    }
    console.log('OverviewComponent constructor');
    this.httpClient
      .get<BoardDefinition[]>('api/boards', {
        headers: {
          Authorization: 'Basic ' + localStorage.getItem('auth'),
        },
      })
      .subscribe((data) => {
        this.boards = data;
      });
  }

  openBoard(name: string) {
    this.router.navigate(['/list', name]);
  }

  onKeyDown($event: KeyboardEvent) {
    if ($event.key === 'Enter') {
      this.createNewBoard();
    }
  }

  createNewBoard() {
    this.showDialog = false;
    this.httpClient
      .post<
        BoardDefinition[]
      >('api/board/' + this.boardNameFormControl.value, {})
      .subscribe((data) => {
        this.boards = data;
      });
    this.boardNameFormControl.reset();
  }

  cancel() {
    this.showDialog = false;
    this.boardNameFormControl.reset();
  }

  deleteBoard(id: string) {
    this.dialog.open(DeletionDialogComponent, {
      data: { id: id, name: name },
    });
  }

  logout() {
    this.httpClient.get('auth/logout').subscribe(() => {
      localStorage.removeItem('auth');
      this.router.navigate(['/login']);
    });
  }
}
