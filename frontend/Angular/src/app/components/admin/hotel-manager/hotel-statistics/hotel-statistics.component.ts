import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { GetBookingResponse } from '../../../../models/response/booking/get-booking-response';

interface Invoice {
    date: string;
    invoiceNumber: string;
    customer: string;
    amount: string;
    status: string;
}

@Component({
  selector: 'app-hotel-statistics',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './hotel-statistics.component.html',
  styleUrl: './hotel-statistics.component.css'
})
export class HotelStatisticsComponent implements OnInit{
  isOpenDetailInvoice: boolean = false;

  bookingResponse: GetBookingResponse = new GetBookingResponse(1, new Date(), 200.00, 123, 1, 456);
  invoices: Invoice[] = [
    { date: '01 Jan 2045', invoiceNumber: 'INV-0123', customer: 'John Doe', amount: '$123', status: 'Paid' },
    { date: '02 Jan 2045', invoiceNumber: 'INV-0124', customer: 'Jane Smith', amount: '$200', status: 'Pending' },
    { date: '03 Jan 2045', invoiceNumber: 'INV-0125', customer: 'Alice Brown', amount: '$150', status: 'Paid' },
    { date: '04 Jan 2045', invoiceNumber: 'INV-0126', customer: 'Bob White', amount: '$80', status: 'Paid' },
    { date: '05 Jan 2045', invoiceNumber: 'INV-0127', customer: 'Charlie Black', amount: '$300', status: 'Pending' },
    { date: '01 Jan 2045', invoiceNumber: 'INV-0123', customer: 'John Doe', amount: '$123', status: 'Paid' },
    { date: '02 Jan 2045', invoiceNumber: 'INV-0124', customer: 'Jane Smith', amount: '$200', status: 'Pending' },
    { date: '03 Jan 2045', invoiceNumber: 'INV-0125', customer: 'Alice Brown', amount: '$150', status: 'Paid' },
    { date: '04 Jan 2045', invoiceNumber: 'INV-0126', customer: 'Bob White', amount: '$80', status: 'Paid' },
    { date: '05 Jan 2045', invoiceNumber: 'INV-0127', customer: 'Charlie Black', amount: '$300', status: 'Pending' },
    { date: '01 Jan 2045', invoiceNumber: 'INV-0123', customer: 'John Doe', amount: '$123', status: 'Paid' },
    { date: '02 Jan 2045', invoiceNumber: 'INV-0124', customer: 'Jane Smith', amount: '$200', status: 'Pending' },
    { date: '03 Jan 2045', invoiceNumber: 'INV-0125', customer: 'Alice Brown', amount: '$150', status: 'Paid' },
    { date: '04 Jan 2045', invoiceNumber: 'INV-0126', customer: 'Bob White', amount: '$80', status: 'Paid' },
    { date: '05 Jan 2045', invoiceNumber: 'INV-0127', customer: 'Charlie Black', amount: '$300', status: 'Pending' },
    { date: '01 Jan 2045', invoiceNumber: 'INV-0123', customer: 'John Doe', amount: '$123', status: 'Paid' },
    { date: '02 Jan 2045', invoiceNumber: 'INV-0124', customer: 'Jane Smith', amount: '$200', status: 'Pending' },
    { date: '03 Jan 2045', invoiceNumber: 'INV-0125', customer: 'Alice Brown', amount: '$150', status: 'Paid' },
    { date: '04 Jan 2045', invoiceNumber: 'INV-0126', customer: 'Bob White', amount: '$80', status: 'Paid' },
    { date: '05 Jan 2045', invoiceNumber: 'INV-0127', customer: 'Charlie Black', amount: '$300', status: 'Pending' },
  ];

  filteredInvoices: Invoice[] = [];
  paginatedInvoices: Invoice[] = [];
  startDate: string = '';
  endDate: string = '';
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalPages: number = 1;

  ngOnInit() {
    this.filteredInvoices = this.invoices; // Khởi tạo danh sách hóa đơn đã lọc
    this.updatePagination();
  }

  onFilter() {
    if (this.startDate && this.endDate) {
      this.filteredInvoices = this.invoices.filter(invoice => {
        const date = new Date(invoice.date);
        return date >= new Date(this.startDate) && date <= new Date(this.endDate);
      });
      this.currentPage = 1; // Reset trang hiện tại
      this.updatePagination();
    }
  }

  updatePagination() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    this.paginatedInvoices = this.filteredInvoices.slice(startIndex, startIndex + this.itemsPerPage);
    this.totalPages = Math.ceil(this.filteredInvoices.length / this.itemsPerPage);
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  openModal(){
    this.isOpenDetailInvoice = true;
  }

  closeModal(){
    this.isOpenDetailInvoice = false;
  }
}
