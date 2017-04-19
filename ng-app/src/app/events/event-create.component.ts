import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { EventService } from './events.service';
import { Event } from './events.model';
import { LoginService } from '../login/login.service';

@Component({
  templateUrl: './event-create.component.html',
  styleUrls:['./event-create.component.css']
})
export class EventCreateComponent{

  name:string;
  place:string;
  price:number;
  start_date:string;
  end_date:string;
  sport:string;
  info:string;

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: EventService,private sessionService: LoginService){}

  eventcreate(){
    let event: Event;
    event = {name: this.name, place: this.place, price:this.price,start_date:this.start_date,end_date:this.end_date,sport:this.sport,info:this.info};
    this.service.createEvent(event).subscribe(
        event => console.log(event)
    );
  }
}