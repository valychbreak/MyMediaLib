import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveCollectionDialogComponent } from './remove-collection-dialog.component';

describe('RemoveCollectionDialogComponent', () => {
  let component: RemoveCollectionDialogComponent;
  let fixture: ComponentFixture<RemoveCollectionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveCollectionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveCollectionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
