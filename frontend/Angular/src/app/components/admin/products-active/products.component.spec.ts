import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductActiveComponent } from './products.component';

describe('ProductsComponent', () => {
  let component: ProductActiveComponent;
  let fixture: ComponentFixture<ProductActiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductActiveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductActiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
