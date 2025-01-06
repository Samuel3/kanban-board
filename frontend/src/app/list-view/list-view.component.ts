import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  CdkDrag,
  CdkDropList,
  CdkDropListGroup,
  CdkDragDrop,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Board, Task } from './list-view.types';
import { NewItemComponent } from '../new-item/new-item.component';
import { debounceTime, Subject } from 'rxjs';

@Component({
  selector: 'app-list-view',
  standalone: true,
  imports: [
    CommonModule,
    CdkDrag,
    CdkDropList,
    CdkDropListGroup,
    MatButtonModule,
    FormsModule,
    NewItemComponent,
  ],
  templateUrl: './list-view.component.html',
  styleUrl: './list-view.component.css',
})
export class ListViewComponent implements OnInit {
  title = 'todo-frontend';
  httpClient = inject(HttpClient);
  board!: Board;
  id = '';
  sync$ = new Subject();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.id = this.route.snapshot.paramMap.get('id') || '';
    this.httpClient.get<Board>('api/board/' + this.id).subscribe((data) => {
      this.board = data;
    });
    this.sync$.pipe(debounceTime(300)).subscribe(() => {
      this.syncList();
    });
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id') || '';
  }

  drop(event: CdkDragDrop<Task[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
    }
    this.sync$.next(1);
  }

  back() {
    console.log('back');
    this.router.navigate(['/']);
  }

  addColumn() {
    this.board.columns.push({
      name: 'New Column',
      id: this.generateRandomId(),
      tasks: [],
    });
  }

  change() {
    this.sync$.next(1);
  }

  addTodo(name: string, index: number) {
    this.board.columns[index].tasks.push({
      title: name,
      id: this.generateRandomId(),
    });
    this.sync$.next(1);
  }

  syncList() {
    const sub = this.httpClient
      .put('api/board/' + this.id, this.board)
      .subscribe(() => {
        console.log('done');
        sub.unsubscribe();
      });
  }

  updateTask(event: KeyboardEvent, index: number, taskIndex: number) {
    this.board.columns[index].tasks[taskIndex].title = (
      event.target as HTMLInputElement
    ).value;
    this.sync$.next(1);
  }

  generateRandomId() {
    return Math.floor(Math.random() * 100000);
  }

  deleteItem(index: number, taskIndex: number) {
    this.board.columns[index].tasks.splice(taskIndex, 1);
    this.sync$.next(1);
  }
}
