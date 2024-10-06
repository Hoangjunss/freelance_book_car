import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-custom-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './custom-modal.component.html',
  styleUrls: ['./custom-modal.component.css']
})
export class CustomModalComponent {
  @Input() title = 'Xác nhận';
  @Input() message = 'Bạn có chắc chắn muốn tiếp tục không?';
  @Input() isVisible = false;
  
  @Output() confirmed = new EventEmitter<void>();
  @Output() closed = new EventEmitter<void>();

  confirm() {
    this.confirmed.emit();
    this.isVisible = false; // Đóng modal sau khi xác nhận
  }

  close() {
    this.closed.emit();
    this.isVisible = false; // Đóng modal sau khi hủy
  }
}
