import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThanhvienAdminComponent } from './thanhvien.component';

describe('ThanhvienComponent', () => {
  let component: ThanhvienAdminComponent;
  let fixture: ComponentFixture<ThanhvienAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThanhvienAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThanhvienAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
