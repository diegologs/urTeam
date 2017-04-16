import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './login.service';
import { User } from '../user/user.model';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  private userLogged:User;


  constructor(private sessionService: LoginService, private router: Router) {
  }

  logIn(username: string, password: string) {
    this.sessionService.logIn(username, password).subscribe(
      user => {
        this.userLogged = user;
        this.router.navigate(['']);
      },
      error => console.log("Fail trying to login.")
    );
  }

  logOut() {
    this.sessionService.logOut();
  }
}