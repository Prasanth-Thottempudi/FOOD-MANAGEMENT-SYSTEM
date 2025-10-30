import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessPatnerRegistrationFormComponent } from './business-patner-registration-form.component';

describe('BusinessPatnerRegistrationFormComponent', () => {
  let component: BusinessPatnerRegistrationFormComponent;
  let fixture: ComponentFixture<BusinessPatnerRegistrationFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BusinessPatnerRegistrationFormComponent]
    });
    fixture = TestBed.createComponent(BusinessPatnerRegistrationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
