/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MovieShortViewComponent } from './movie-short-view.component';

describe('MovieShortViewComponent', () => {
  let component: MovieShortViewComponent;
  let fixture: ComponentFixture<MovieShortViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MovieShortViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieShortViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
