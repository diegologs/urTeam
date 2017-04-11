import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {EventService} from './events.service';

@Component({
  selector: 'events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit{

  events: Event[];
  constructor(private router:Router, private service: EventService){}

  ngOnInit(){
    this.service.getEvents().subscribe(
        events => this.events = events.content,
        error => console.log(error)
    )
  }
}