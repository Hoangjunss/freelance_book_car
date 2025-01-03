import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersPendingComponent } from './orders.component';

describe('OrdersComponent', () => {
  let component: OrdersPendingComponent;
  let fixture: ComponentFixture<OrdersPendingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdersPendingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdersPendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
