import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule} from '@angular/material/input';
import { FormsModule, Validators, FormControl, FormGroupDirective, NgForm, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpClient } from '@angular/common/http';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatInputModule, FormsModule, MatButtonModule, MatFormFieldModule, ReactiveFormsModule],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css',
})
export class OverviewComponent {

  boards:string[] = []
  showDialog = false;
  boardNameFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();
  httpClient = inject(HttpClient);

  constructor(private router: Router) {
    console.log("OverviewComponent constructor");
    this.httpClient.get<string[]>('api/boards').subscribe(data => {
      this.boards = data;
    });
  }

  openBoard(name: string) {
    this.router.navigate(['/list', name]);
  }

  createNewBoard() {
    this.showDialog = false;
    this.boards.push(this.boardNameFormControl.value?.toString() || "");
    this.boardNameFormControl.reset();
  }

  cancel() {
    this.showDialog = false;
    this.boardNameFormControl.reset();
  }

}
