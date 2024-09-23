import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { GetTourResponse } from '../../../../models/response/product/tour/tour/get-tour-response';

@Component({
  selector: 'app-tour',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tour.component.html',
  styleUrl: './tour.component.css'
})
export class TourComponent implements OnInit{
  isDisplayDetails: boolean = false;
  tour?: GetTourResponse;
  data: any[] = [];
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: any[] = [];

  ngOnInit(): void {
    this.data = this.getData();
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.data.slice(startIndex, endIndex);
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  get totalPages(): number {
    return Math.ceil(this.data.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  getData() {
    return [
      { id: 1, firstName: 'John', lastName: 'Doe', email: 'john@email.com', country: 'USA', zip: '12345' },
      { id: 2, firstName: 'Mark', lastName: 'Otto', email: 'mark@email.com', country: 'UK', zip: '45678' },
      { id: 3, firstName: 'Jacob', lastName: 'Thornton', email: 'jacob@email.com', country: 'AU', zip: '78910' },
      { id: 4, firstName: 'Chris', lastName: 'Evans', email: 'chris@email.com', country: 'USA', zip: '22345' },
      { id: 5, firstName: 'Paul', lastName: 'Walker', email: 'paul@email.com', country: 'UK', zip: '55678' },
      { id: 6, firstName: 'Sam', lastName: 'Smith', email: 'sam@email.com', country: 'AU', zip: '88910' },
      { id: 7, firstName: 'Emma', lastName: 'Stone', email: 'emma@email.com', country: 'USA', zip: '32345' },
      { id: 8, firstName: 'Tom', lastName: 'Cruise', email: 'tom@email.com', country: 'UK', zip: '65678' },
      { id: 9, firstName: 'Leo', lastName: 'DiCaprio', email: 'leo@email.com', country: 'AU', zip: '98910' },
      { id: 10, firstName: 'Ryan', lastName: 'Reynolds', email: 'ryan@email.com', country: 'USA', zip: '42345' },
    ];
  }

  displayDetailsTour(){
    this.isDisplayDetails = true;
  }

  save() {
    
    if(this.tour != undefined){
      console.log('Saved:', {
      id: this.tour.id,
      name: this.tour.name,
      description: this.tour.description,
      startLocation: this.tour.startLocation,
      endLocation: this.tour.endLocation,
      idTourStatus: this.tour.idTourStatus,
    });
    }
    
  }

  // Hàm để xử lý sự kiện Cancel
  cancel() {
    this.isDisplayDetails = false;
    // Logic để hủy
    console.log('Cancelled');
  }

}
