import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplierAccessAdminComponent } from './supplier.component';

describe('SupplierComponent', () => {
  let component: SupplierAccessAdminComponent;
  let fixture: ComponentFixture<SupplierAccessAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SupplierAccessAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupplierAccessAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
