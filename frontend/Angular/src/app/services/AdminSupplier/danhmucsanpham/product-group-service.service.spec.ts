import { TestBed } from '@angular/core/testing';

import { ProductGroupServiceService } from './product-group-service.service';

describe('ProductGroupServiceService', () => {
  let service: ProductGroupServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductGroupServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
