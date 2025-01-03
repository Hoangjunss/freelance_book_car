import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductActiveAdminComponent } from './products.component';

describe('ProductsComponent', () => {
  let component: ProductActiveAdminComponent;
  let fixture: ComponentFixture<ProductActiveAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductActiveAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductActiveAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
