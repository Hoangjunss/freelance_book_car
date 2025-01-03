import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportAdminComponent } from './support.component';

describe('SupportComponent', () => {
  let component: SupportAdminComponent;
  let fixture: ComponentFixture<SupportAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SupportAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupportAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});