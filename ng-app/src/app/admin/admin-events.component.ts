import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from "app/events/events.service";
import { LoginService } from "app/login/login.service";

@Component({
  selector: 'admin-events',
  templateUrl: './admin-events.component.html',
  styleUrls: ['../events/events.component.css']
})
export default class AdminEvents implements OnInit {

  eventsPage: number;
  eventsPageActual: number;
  moreEventsButtonText: string;

  events: Event[];

  imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router: Router, private service: EventService, private sessionService: LoginService) { }

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

deleteEvent(id: number){

    this.service.deleteEvent(id).subscribe(
      response => {
        
        this.getEvents();
        
      },
      error => console.log(error),
    )
   


  }

  getEvents() {
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
  
 

}