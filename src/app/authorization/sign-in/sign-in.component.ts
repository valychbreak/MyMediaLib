import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {MovieDetailsModalComponent} from "../../movies/movie-details-modal/movie-details-modal.component";

@Component({
  selector: 'app-sign-in',
  templateUrl: 'sign-in.component.html',
  styleUrls: ['sign-in.component.css']
})
export class SignInComponent implements OnInit {
  title: string;
  model = 1;

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
    this.title = "Sign in"
  }

  open() {
    const modalRef = this.modalService.open(MovieDetailsModalComponent);
    modalRef.componentInstance.name = 'World';
  }

}
