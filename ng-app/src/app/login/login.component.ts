import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './login.service';
import { User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { HttpClient } from '../HttpClient/httpClient';

import {PublicComponent} from '../public.component';

@Component({
  selector: 'navbar2',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent{

  //private userLogged:User;

  username:string;
  surname:string;
  nickname:string;
  email:string;
  city:string;
  country:string;
  password:string;
  
  constructor(private http: HttpClient,private sessionService: LoginService, private router: Router, private userService: UserService, private pubComponent: PublicComponent) {
  }

  logIn(username: string, password: string) {
    this.sessionService.logIn(username, password).subscribe(
      user => {
        this.pubComponent.msgs.push({severity:'success', summary:'Inicio de sesión', detail:'Bienvenido a urTeam'});
      },
      error => {
        this.pubComponent.msgs.push({severity:'error', summary:'Error en inicio sesión', detail:'Revise nombre de usuario y contraseña'});
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
        this.sessionService.logOut();
    }

  register(){
    let user: User;
    user = {username: this.username, surname: this.surname, nickname: this.nickname, email: this.email, city: this.city, country: this.country, password: this.password};
    this.userService.createUser(user).subscribe(
        event => console.log(event)
    );
    this.router.navigateByUrl('');
  }
}