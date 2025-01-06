import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { MatButton } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-deletion-dialog',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatButton,
  ],
  templateUrl: './deletion-dialog.component.html',
  styleUrl: './deletion-dialog.component.scss',
})
export class DeletionDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DeletionDialogComponent>,
    private httpClient: HttpClient,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
  ) {}

  cancel(): void {
    this.dialogRef.close();
  }

  delete() {
    this.dialogRef.close(true);
    this.httpClient.delete(`api/board/${this.data.id}`).subscribe();
    window.location.reload();
  }
}
