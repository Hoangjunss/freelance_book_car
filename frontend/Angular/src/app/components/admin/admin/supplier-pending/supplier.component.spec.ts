import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplierPendingAdminComponent } from './supplier.component';

describe('SupplierComponent', () => {
  let component: SupplierPendingAdminComponent;
  let fixture: ComponentFixture<SupplierPendingAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SupplierPendingAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupplierPendingAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
