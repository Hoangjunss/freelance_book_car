import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LienheAdminComponent } from './lienhe.component';

describe('LienheComponent', () => {
  let component: LienheAdminComponent;
  let fixture: ComponentFixture<LienheAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LienheAdminComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LienheAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
