import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgenthomeComponent } from './agenthome.component';

describe('AgenthomeComponent', () => {
  let component: AgenthomeComponent;
  let fixture: ComponentFixture<AgenthomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgenthomeComponent]
    });
    fixture = TestBed.createComponent(AgenthomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
