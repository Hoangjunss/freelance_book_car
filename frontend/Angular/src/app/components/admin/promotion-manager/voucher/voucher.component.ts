import { GetVoucherResponse } from './../../../../models/response/product/voucher/voucher/get-voucher-response';
import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NoDataFoundComponent } from "../../no-data-found/no-data-found.component";
import { CreateVoucherRequest } from '../../../../models/request/product/voucher/voucher/create-voucher-request';
import { CreateVoucherResponse } from '../../../../models/response/product/voucher/voucher/create-voucher-response';
import { UpdateVoucherRequest } from '../../../../models/request/product/voucher/voucher/update-voucher-request';
import { UpdateVoucherResponse } from '../../../../models/response/product/voucher/voucher/update-voucher-response';
import { VoucherService } from '../../../../services/product/voucher/voucher/voucher.service';
import { NotificationComponent } from '../../../notification/notification.component';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-voucher',
  standalone: true,
  imports: [CommonModule, FormsModule, NoDataFoundComponent],
  templateUrl: './voucher.component.html',
  styleUrls: ['./voucher.component.css']
})
export class VoucherComponent implements OnInit {
  createVoucherRequest: CreateVoucherRequest = new CreateVoucherRequest();
  createVoucherResponse: CreateVoucherResponse = new CreateVoucherResponse();
  updateVoucherRequest: UpdateVoucherRequest = new UpdateVoucherRequest();
  updateVoucherResponse: UpdateVoucherResponse = new UpdateVoucherResponse();

  //isDisplayDetails: boolean = false;
  isDisplayCreate = false;
  isDisplayUpdate = false;
  selectedTourId: number | null | undefined = null; // Chấp nhận undefined
  isEditMode: boolean = false;
  currentPage: number = 1;
  pageSize: number = 5;
  pagedData: GetVoucherResponse[] = [];

  selectedSchedule: GetVoucherResponse[] = [];

  getVoucherResponse: GetVoucherResponse [] = [];
  filterVoucher: GetVoucherResponse[] = []; 

  searchQuery: string='';
  @ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;
  constructor(private voucherService: VoucherService, private title:Title) {
    this.title.setTitle('Quản lý voucher')
  }

  ngOnInit(): void {
    this.getAllVouchers();
    this.updatePagedData();
    console.log(this.selectedSchedule);
  }

  getAllVouchers() {
    this.voucherService.getAll().subscribe({
      next: (data) => {
        this.getVoucherResponse = data; // Cập nhật danh sách voucher
        console.log('All vouchers:', this.getVoucherResponse);
        this.updatePagedData();
      },
      error: (err) => {
        console.error('Error getting vouchers:', err.message);
      }
    });
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.updatePagedData();
  }

  updatePagedData() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedData = this.getVoucherResponse.slice(startIndex, endIndex);
  }

  get totalPages(): number {
    return Math.ceil(this.getVoucherResponse.length / this.pageSize);
  }

  get pages(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  displayFormCreate() {
    this.isDisplayCreate = true;
  }

  closeFormCreate() {
    this.isDisplayCreate = false;
  }

  displayFormUpdate(voucher: GetVoucherResponse) {
    this.updateVoucherRequest = {
      id: voucher.id,
      name: voucher.name, // Giả sử có trường name trong voucher
      discountRate: voucher.discountRate, // Giả sử có trường discountRate
      endDate: voucher.endDate, // Giả sử có trường endDate
      isUse: voucher.isUse // Giả sử có trường isUse
    };
    this.isEditMode = true;
    this.isDisplayUpdate = true;
  }

  closeFormUpdate() {
    this.isEditMode = false;
    this.isDisplayUpdate = false;
  }

  onCreateVoucher() {
    if (!this.createVoucherRequest?.name || !this.createVoucherRequest?.discountRate || !this.createVoucherRequest?.endDate) {
      this.notificationComponent.showNotification('error', 'Vui lòng điền đầy đủ thông tin bắt buộc: Tên, Tỷ lệ giảm giá, Ngày kết thúc');
      return;
    }
    const formData = new FormData();
    formData.append('isUse', this.createVoucherRequest.isUse?.toString() ?? '');
    formData.append('name', this.createVoucherRequest.name ?? '');
    formData.append('discountRate', this.createVoucherRequest.discountRate?.toString() ?? '');

    if(this.createVoucherRequest.endDate!=undefined ){
      const endDate = new Date(this.createVoucherRequest.endDate);  
      const endDateWithoutTimezone = endDate.toISOString().slice(0, 19);
      formData.append('endDate', endDateWithoutTimezone);
    }

    this.voucherService.createVoucher(formData).subscribe({
      next: (data) => {
        this.createVoucherResponse = data;
        if (this.createVoucherResponse) {
          this.notificationComponent.showNotification('success', 'Tạo vouder thành công');
          this.getAllVouchers(); // Làm mới danh sách
          this.closeFormCreate();
        }
      },
      error: (err) => {
        
      }
    });
  }

  onUpdateVoucher() {
    if (!this.updateVoucherRequest?.id) {
      this.notificationComponent.showNotification('error', 'Không tìm thấy voucher cần cập nhật');
      return;
    }
    const formData = new FormData();
    formData.append('id', this.updateVoucherRequest.id?.toString() ?? '');
    formData.append('isUse', this.updateVoucherRequest.isUse?.toString() ?? '');
    formData.append('name', this.updateVoucherRequest.name ?? '');
    formData.append('discountRate', this.updateVoucherRequest.discountRate?.toString() ?? '');

    if(this.updateVoucherRequest.endDate!=undefined ){
      const endDate = new Date(this.updateVoucherRequest.endDate);  
      const endDateWithoutTimezone = endDate.toISOString().slice(0, 19);
      formData.append('endDate', endDateWithoutTimezone);
    }

    this.voucherService.updateVoucher(formData).subscribe({
      next: (data) => {
        this.updateVoucherResponse = data;
        if (this.updateVoucherResponse) {
          console.log('Voucher updated successfully:', this.updateVoucherResponse);
          this.notificationComponent.showNotification('success', 'Cập nhật voucher thành công');
          this.getAllVouchers(); // Làm mới danh sách
          this.closeFormUpdate();
        }
      },
      error: (err) => {
        console.error('Error updating voucher:', err.message);
      }
    });
  }

  searchTour(){
    this.filterVoucher = this.getVoucherResponse.filter((voucher) => {
      return voucher.name?.toLowerCase().includes(this.searchQuery.toLowerCase());
    });
    this.updatePagedData();
  }

  reset(){
    this.searchQuery = '';
    this.filterVoucher = this.getVoucherResponse;
    this.updatePagedData
  }
}
