import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartOrderAssistantComponent } from './smart-order-assistant.component';

describe('SmartOrderAssistantComponent', () => {
  let component: SmartOrderAssistantComponent;
  let fixture: ComponentFixture<SmartOrderAssistantComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SmartOrderAssistantComponent]
    });
    fixture = TestBed.createComponent(SmartOrderAssistantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
