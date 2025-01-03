import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductPendingAdminComponent } from './products.component';

describe('ProductsComponent', () => {
  let component: ProductPendingAdminComponent;
  let fixture: ComponentFixture<ProductPendingAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductPendingAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductPendingAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
