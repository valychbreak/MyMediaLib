import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCategoryViewComponent } from './new-collection-view.component';

describe('NewCategoryViewComponent', () => {
  let component: NewCategoryViewComponent;
  let fixture: ComponentFixture<NewCategoryViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewCategoryViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCategoryViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
