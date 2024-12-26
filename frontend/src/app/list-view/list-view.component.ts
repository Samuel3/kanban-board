import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdkDrag, CdkDropList, CdkDropListGroup, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Board, Column, Task } from './list-view.types';
import { getDomElements } from '@angular-eslint/eslint-plugin-template/dist/utils/get-dom-elements';
import { NewItemComponent } from '../new-item/new-item.component';

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

  lists: Column[] = [];
  id: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) {
    this.httpClient.get<Board>('api/board/4711').subscribe((data) => {
      console.log(data);
      this.lists = data.columns;
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
    this.synchList();
  }

  back() {
    console.log('back');
    this.router.navigate(['/']);
  }

  addColumn() {
    this.lists.push({ name: 'New Column', id: '', tasks: [] });
  }

  change() {
    console.log('change');
  }

  addTodo(name: string, index: number) {
    this.lists[index].tasks.push({ title: name, id: '' });
  }

  down(event: KeyboardEvent, index: number) {
    if (event.key === 'Enter') {
      this.addTodo((event.target as HTMLInputElement).value, index);
      (event.target as HTMLInputElement).value = '';
    }
    this.synchList();
  }

  clickOk(index: number) {
    const contentHolder = document.getElementById(
      index.toString(),
    ) as HTMLInputElement;
    const todoItem = contentHolder.value ?? '';
    this.addTodo(todoItem, index);
    contentHolder.value = '';
    this.synchList();
  }

  synchList() {
    console.log('synchList');
  }

  updateTask(event: KeyboardEvent, index: number, taskIndex: number) {
    if (event.key === 'Enter') {
      this.lists[index].tasks[taskIndex].title = (
        event.target as HTMLInputElement
      ).value;
      this.synchList();
    }
  }
}
