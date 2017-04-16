import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from './user.service';
import { User } from './user.model';
import { Event } from '../events/events.model';
import { Community } from '../communities/community.model';

@Component({
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent{
  user: User;

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: UserService){
      let nickname = activatedRoute.snapshot.params['nickname'];
      service.getUser(nickname).subscribe(
          user => {
            this.user = user
          },
          error => console.error(error)
      );
  }
}