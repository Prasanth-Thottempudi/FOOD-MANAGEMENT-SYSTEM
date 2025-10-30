import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentdashboardComponent } from './agentdashboard.component';

describe('AgentdashboardComponent', () => {
  let component: AgentdashboardComponent;
  let fixture: ComponentFixture<AgentdashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgentdashboardComponent]
    });
    fixture = TestBed.createComponent(AgentdashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
