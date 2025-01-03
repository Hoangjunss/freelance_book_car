import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { supplierRouter } from './supplier.routes';

@NgModule({
  declarations: [],
  imports: [CommonModule,RouterModule.forChild(supplierRouter)]
})
export class SupplierModule { }
