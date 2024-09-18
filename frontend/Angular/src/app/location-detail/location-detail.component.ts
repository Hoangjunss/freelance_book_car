import { CommonModule } from '@angular/common';
import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-location-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './location-detail.component.html',
  styleUrl: './location-detail.component.css'
})
export class LocationDetailComponent {
  @Input() location: string | null = null;
}
