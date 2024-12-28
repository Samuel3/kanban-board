import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-item',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './new-item.component.html',
  styleUrl: './new-item.component.scss',
})
export class NewItemComponent {

  @Input() callfn: (newName: string, index: number) => void = () => { console.log() };
  @Input() index = 0;
  typeValue = '';


  onTyping(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.createNewItem();
    }
  }

  createNewItem() {
    this.callfn(this.typeValue, this.index);
    this.typeValue = '';
  }
}
