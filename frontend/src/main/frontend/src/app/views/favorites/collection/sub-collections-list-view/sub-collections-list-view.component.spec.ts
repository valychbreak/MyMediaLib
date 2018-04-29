import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubCategoriesListViewComponent } from './sub-collections-list-view.component';

describe('SubCategoriesListViewComponent', () => {
  let component: SubCategoriesListViewComponent;
  let fixture: ComponentFixture<SubCategoriesListViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubCategoriesListViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubCategoriesListViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
