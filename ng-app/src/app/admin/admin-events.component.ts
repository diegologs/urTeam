import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from "app/events/events.service";
import { LoginService } from "app/login/login.service";
import {PrivateComponent} from './admin.component';

@Component({
  selector: 'admin-events',
  templateUrl: './admin-events.component.html',
  styleUrls: ['../events/events.component.css']
})
export default class AdminEvents implements OnInit {

  eventsPage: number;
  eventsPageActual: number;
  moreEventsButtonText: string;

  msg: string;

  events: Event[];

  imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router: Router, private service: EventService, private sessionService: LoginService,private msgComponent: PrivateComponent) { }

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
        this.msgComponent.msgs.push({severity:'success', summary:'Evento eliminado', detail:'Se ha eliminado el evento'});         
      },
      error => {
        console.log(error);
        this.msgComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un errror durante la eliminación del evento'}); 
      }
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