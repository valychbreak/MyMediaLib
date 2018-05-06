import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PersonShortViewComponent} from './person-short-view.component';

describe('PersonShortViewComponent', () => {
    let component: PersonShortViewComponent;
    let fixture: ComponentFixture<PersonShortViewComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PersonShortViewComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PersonShortViewComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
