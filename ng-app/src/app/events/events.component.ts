import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from './events.service';

@Component({
  selector: 'events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  eventsPage: number;
  eventsPageActual: number;
  moreEventsButtonText: string;

  events: Event[];

  imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router: Router, private service: EventService) { }

  ngOnInit() {
    this.eventsPage = 0;
    this.moreEventsButtonText = "Ver Más";
    this.service.getEvents(this.eventsPage).subscribe(
      events => {
        this.eventsPage++;
        this.events = events.content;
        this.eventsPageActual = events.totalPages;
      },
      error => {
        console.log(error);
      }
    )
  }

  moreEvents() {
    if (this.eventsPage < this.eventsPageActual) {
      this.service.getEvents(this.eventsPage).subscribe(
        events => {
          this.eventsPage++;
          this.events = this.events.concat(events.content);
        },
        error => {
          console.log(error);
        }
      )
    }
    else {
      this.moreEventsButtonText = 'No hay mas resultados';
    }
  }
}