import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PeopleSearchSectionComponent } from './people-search-section.component';

describe('PeopleSearchSectionComponent', () => {
  let component: PeopleSearchSectionComponent;
  let fixture: ComponentFixture<PeopleSearchSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PeopleSearchSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PeopleSearchSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
