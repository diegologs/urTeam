import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './login.service';
import { User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { HttpClient } from '../HttpClient/httpClient';

@Component({
  selector: 'navbar2',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent{

  private userLogged:User;

  username:string;
  surname:string;
  nickname:string;
  email:string;
  city:string;
  country:string;
  passwordHash:string;

  
  constructor(private http: HttpClient,private sessionService: LoginService, private router: Router, private userService: UserService) {
  }

  logIn(username: string, password: string) {
    this.sessionService.logIn(username, password).subscribe(
      user => {
        this.userLogged = user;
      },
      error => {
        console.log("Fail trying to login.")
        this.http.sessionData.isLogged = false;
      }
    );
    this.router.navigateByUrl('');
  }

  checkLogged(){
    this.sessionService.getisLogged();
  }

  logOut() {
    this.sessionService.logOut();
  }

  navbarLogOut(){
        this.sessionService.logOut().subscribe()
    }

  register(){
    let user: User;
    user = {username: this.username, surname: this.surname, nickname: this.nickname, email: this.email, city: this.city, country: this.country, passwordHash: this.passwordHash};
    this.userService.createUser(user).subscribe(
        event => console.log(event)
    );
    this.router.navigateByUrl('');
  }
}