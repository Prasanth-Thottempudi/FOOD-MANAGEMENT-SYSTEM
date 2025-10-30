import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomersupportComponent } from './customersupport.component';

describe('CustomersupportComponent', () => {
  let component: CustomersupportComponent;
  let fixture: ComponentFixture<CustomersupportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomersupportComponent]
    });
    fixture = TestBed.createComponent(CustomersupportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
