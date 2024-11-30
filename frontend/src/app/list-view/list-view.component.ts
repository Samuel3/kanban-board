import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CdkDrag, CdkDropList, CdkDropListGroup, CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

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
  ],
  templateUrl: './list-view.component.html',
  styleUrl: './list-view.component.css',
})
export class ListViewComponent implements OnInit {
  title = 'todo-frontend';

  lists = [
    { name: 'Todo', tasks: ['1st', '2nd', '3rd'] },
    { name: 'In Progress', tasks: ['4th', '5th'] },
    { name: 'Done', tasks: [] },
  ];
  id: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id') || '';
  }

  drop(event: CdkDragDrop<string[]>) {
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
    this.lists.push({ name: 'New Column', tasks: [] });
  }

  change() {
    console.log('change');
  }

  addTodo(name: string, index: number) {
    this.lists[index].tasks.push(name);
  }

  down(event: KeyboardEvent, index: number) {
    if (event.key === 'Enter') {
      this.addTodo(
        (event.target as HTMLInputElement).value,
        index,
      );
      (event.target as HTMLInputElement).value = '';
    }
    this.synchList();
  }

  synchList() {
    console.log('synchList');
  }

  updateTask(event: KeyboardEvent, index: number, taskIndex: number) {
    if (event.key === 'Enter') {
      this.lists[index].tasks[taskIndex] = (event.target as HTMLInputElement).value;
      this.synchList();
    }
  }
}
