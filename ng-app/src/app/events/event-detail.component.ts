import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { EventService } from './events.service';
import { Event } from './events.model';

@Component({
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent{

  event: Event;


  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: EventService){
      let id = activatedRoute.snapshot.params['id'];
      service.getEvent(id).subscribe(
          event => {
            this.event = event
          },
          error => console.error(error)
      );
  }


}