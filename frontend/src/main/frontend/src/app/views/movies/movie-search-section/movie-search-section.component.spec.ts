import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieSearchSectionComponent } from './movie-search-section.component';

describe('MovieSearchSectionComponent', () => {
  let component: MovieSearchSectionComponent;
  let fixture: ComponentFixture<MovieSearchSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieSearchSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieSearchSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
