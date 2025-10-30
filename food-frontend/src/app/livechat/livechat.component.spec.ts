import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivechatComponent } from './livechat.component';

describe('LivechatComponent', () => {
  let component: LivechatComponent;
  let fixture: ComponentFixture<LivechatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LivechatComponent]
    });
    fixture = TestBed.createComponent(LivechatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
