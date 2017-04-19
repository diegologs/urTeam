import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { EventService } from './events.service';
import { Event } from './events.model';
import { User } from '../user/user.model';
import { LoginService } from "app/login/login.service";

@Component({
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent {

  event: Event;
  participants_IDs: User[];
  user: User;
  followText: string;
  isFollow: boolean;

  constructor(private sessionService: LoginService, private router: Router, activatedRoute: ActivatedRoute, private service: EventService) {
    let id = activatedRoute.snapshot.params['id'];
    service.getEvent(id).subscribe(
      event => {
        this.event = event
        this.participants_IDs = event.participants_IDs;
        this.user = this.sessionService.getUser();
      },
      error => console.error(error),
      () => this.checkFollow()
    );
  }

  follow() {
    this.service.followEvent(this.event.id).subscribe(
      response => {
        this.participants_IDs = response.participants_IDs;
        console.log(this.participants_IDs);
      },
      error => console.log(error),
    )
    this.checkFollow();
  }

  checkFollow() {
    if (this.sessionService.getisLogged()) {
      for (let follower of this.participants_IDs) {
        if (follower.nickname === this.sessionService.getUser().nickname) {
          this.followText = "Dejar de seguir";
          console.log(this.sessionService.getUser());
        } else {
          this.followText = "Seguir";
          console.log(this.sessionService.getUser());
        }
      }
    }
  }
}