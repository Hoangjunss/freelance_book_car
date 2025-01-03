import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductPendingComponent } from './products.component';

describe('ProductsComponent', () => {
  let component: ProductPendingComponent;
  let fixture: ComponentFixture<ProductPendingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductPendingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductPendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
