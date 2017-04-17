import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Http, Headers } from '@angular/http';

import { User } from '../user/user.model';
import { UserService } from '../user/user.service';

import 'rxjs/Rx';

const BASE_URL = 'https://localhost:8443/api/';

@Injectable()
export class LoginService {

  userLogged: User;
  authCreds: string;
  isLogged = false;
  isAdmin = false;

  constructor(private http: Http, private userService: UserService) {
  }

  logIn(username: string, password: string) {
    console.log("logIn");
    this.authCreds = btoa(username + ':' + password);
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + this.authCreds);

    return this.http.get(BASE_URL + 'logIn', {headers: headers})
      .map(
        response => {
          let nickname = response.json().nickname;
          this.userService.setAuthHeaders(this.authCreds);
          this.userService.getUser(nickname).subscribe(
            user => {
              this.userLogged = user;
              // if(this.userLogged.roles.indexOf("ADMIN") > -1){
              //   this.isAdmin = true;
              // }
            },
            error => console.log(error)
          );
          this.isLogged = true;
          return this.userLogged;
      })
      .catch(error => Observable.throw('Server error'));
  }

  // logOut() {
  //   console.log("logOut");
  //   // let headers: Headers = new Headers();
  //   // headers.append('Authorization', 'Basic ' + this.authCreds);
  //   return this.http.get(BASE_URL + 'logOut')
  //     .map(response => {
  //       this.isLogged = false;
  //       this.userLogged = null;
  //       return true;
  //     })
  //     .catch(error => Observable.throw('Server error'));
  // }

  logOut(){
      console.log("logOut");
        return this.http.get(BASE_URL + 'logOut')
        .map(
            response => {
                this.userLogged = null;
                console.log("logOut");
                this.isLogged = false;
                console.log("logOut");
            }
        );
    }
}