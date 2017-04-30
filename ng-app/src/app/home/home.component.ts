import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from '../events/events.service';
import { LoginService } from '../login/login.service';
import {PublicComponent} from '../public.component';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',  
  styleUrls: ['../events/events.component.css']
})
export class HomeComponent {

  events1: Event[];
  events2: Event[];
  events3: Event[];

  constructor(private router: Router, private service: EventService, private sessionService: LoginService, private pubComponent: PublicComponent) { }

  ngOnInit() {
    this.service.getEventsBySport("Mountain Bike").subscribe(
      events => {
        this.events1 = events.content;
      },
      error => {
        console.log(error);
      }
    )
    this.service.getEventsBySport("Running").subscribe(
      events => {
        this.events2 = events.content;
      },
      error => {
        console.log(error);
      }
    )
    this.service.getEventsBySport("Roller").subscribe(
      events => {
        this.events3 = events.content;
      },
      error => {
        console.log(error);
      }
    )
  }
}