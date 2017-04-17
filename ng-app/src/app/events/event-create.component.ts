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

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: EventService,private sessionService: LoginService){}

  eventcreate(name:string,place:string,price:number,startDate:string,endDate:string,sport:string,info:string){
    let event = new Event(name,sport,price,info,place,startDate,endDate);
    this.service.createEvent(event).subscribe(
        event => console.log(event)
    );
  }
}