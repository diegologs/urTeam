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
            },
            error => console.log(error)
          );
          //localStorage.setItem("user", username);
          this.isLogged = true;
          return this.userLogged;
      })
      .catch(error => Observable.throw('Server error'));
  }

  logOut() {
    let headers: Headers = new Headers();
    headers.append('Authorization', 'Basic ' + this.authCreds);
    return this.http.get(BASE_URL + 'logOut', {headers: headers})
      .map(response => {
        //localStorage.clear();
        this.isLogged = false;
        this.userLogged = null;
        return true;
      })
      .catch(error => Observable.throw('Server error'));
  }

  logged(){
    return this.isLogged;

  }

  /*checkCredentials() {
    return (localStorage.getItem("user") !== null);
  }*/

  /*register(firstName: string, lastName1: string, lastName2: string, username: string, password: string, dni: string, email: string, phone: string){
      let newUser: User;
      newUser = {name: username, passwordHash: password, dni: dni, firstName: firstName, lastName1: lastName1, lastName2: lastName2, email: email, telephone: phone, hasPhoto: false, viewTelephone: false};
      return this.http.post(BASE_URL + 'register', newUser);
  }*/
}