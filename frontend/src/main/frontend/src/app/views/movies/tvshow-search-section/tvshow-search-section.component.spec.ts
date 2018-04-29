import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TvShowSearchSectionComponent } from './tvshow-search-section.component';

describe('TvShowSearchSectionComponent', () => {
  let component: TvShowSearchSectionComponent;
  let fixture: ComponentFixture<TvShowSearchSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TvShowSearchSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TvShowSearchSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
