import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from './user.service';
import { User } from './user.model';

@Component({
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent{

  event: Event;
  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: UserService){
      let nickname = activatedRoute.snapshot.params['nickname'];
      service.getUser(nickname).subscribe(
          event => this.event = event,
          error => console.error(error)
      );
  }
}