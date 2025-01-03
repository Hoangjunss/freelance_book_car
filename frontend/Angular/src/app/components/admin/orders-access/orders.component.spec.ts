import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersAccessComponent } from './orders.component';

describe('OrdersComponent', () => {
  let component: OrdersAccessComponent;
  let fixture: ComponentFixture<OrdersAccessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdersAccessComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdersAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
