import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MediaSearchSectionComponent } from './media-search-section.component';

describe('MediaSearchSectionComponent', () => {
  let component: MediaSearchSectionComponent;
  let fixture: ComponentFixture<MediaSearchSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MediaSearchSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MediaSearchSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
