import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdkDrag, CdkDropList, CdkDropListGroup, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Board, Column, Task } from './list-view.types';
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
    this.httpClient.get<Board>('api/board/4711').subscribe((data) => {
      console.log(data);
      this.board = data;
    });
    this.sync$.pipe(debounceTime(300)).subscribe(() => {
      this.synchList();
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
    this.board.columns.push({ name: 'New Column', id: this.generateRandomId(), tasks: [] });
  }

  change() {
    console.log('change');
  }

  addTodo(name: string, index: number) {
    this.board.columns[index].tasks.push({ title: name, id: this.generateRandomId() });
    this.sync$.next(1);
  }

  synchList() {
    console.log('synchList');
    const sub = this.httpClient.put('api/board/4711', this.board).subscribe(() => {
      console.log('done');
      sub.unsubscribe();
    });
  }

  updateTask(event: KeyboardEvent, index: number, taskIndex: number) {
    if (event.key === 'Enter') {
      this.board.columns[index].tasks[taskIndex].title = (
        event.target as HTMLInputElement
      ).value;
      this.sync$.next(1);
    }
  }

  generateRandomId() {
    return Math.floor(Math.random() * 100000);
  }
}
