import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-input-spinner',
  templateUrl: './input-spinner.component.html',
  styleUrls: ['./input-spinner.component.css'],
})
export class InputSpinnerComponent {
  @Input() quantity: number = 0;
  @Input() min: number = 0;
  @Input() max: number = 0;
  @Output() eventEmitter: EventEmitter<number> = new EventEmitter<number>();

  increase() {
    if (this.quantity >= this.min && this.quantity < this.max) {
      this.quantity++;
    }
    this.eventEmitter.emit(this.quantity);
    return this.quantity;
  }

  decrease() {
    if (this.quantity > this.min && this.quantity <= this.max) {
      this.quantity--;
    }
    this.eventEmitter.emit(this.quantity);
    return this.quantity;
  }
}
