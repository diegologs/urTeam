import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Http, Headers } from '@angular/http';

import { User } from '../user/user.model';
import { UserService } from '../user/user.service';
import { HttpClient } from '../HttpClient/httpClient';

import 'rxjs/Rx';

const BASE_URL_Login = 'https://localhost:8443/api/logIn';
const BASE_URL_Logout = 'https://localhost:8443/api/logOut';

@Injectable()
export class LoginService {

    //userLogged: User;
    //authCreds: string;
    //isLogged = false;
    //isAdmin = false;

    constructor(private http: HttpClient, private userService: UserService) {
    }

    private generateAuthString(username: String, password: String) {
        console.log("User:" + username + " pass: " + password)
        return "Basic " + btoa(username + ":" + password);
    }

    logIn(username: string, password: string) {

        this.http.sessionData.authToken = this.generateAuthString(username, password);
        this.http.sessionData.isLogged = true;
        return this.http.get(BASE_URL_Login).map(
            response => {
                // let nickname = response.json().nickname;
                // this.userService.setAuthHeaders(this.authCreds);
                this.userService.getUser(username).subscribe(
                    user => {
                        this.http.sessionData.userLogged = user;
                        this.http.sessionData.isLogged = true;
                        if (this.http.sessionData.isLogged) {
                            if (this.http.sessionData.userLogged.roles.indexOf("ROLE_ADMIN") > -1) {
                                this.http.sessionData.isAdmin = true;
                                console.log("is admin");
                            }
                        }
                    },
                    error => {
                         console.log("Fallo Login");
                        console.log(error);
                        this.http.sessionData.isLogged = false;
                    }
                );
            })
            .catch(error => this.loginFailed(error));
    }

    logOut() {
        console.log("logOut");
        return this.http.get(BASE_URL_Logout)
            .map(
            response => {
                this.http.sessionData.userLogged = null;
                console.log("logOut");
                console.log(this.http.sessionData.userLogged);
                this.http.sessionData.isLogged = false;
                this.http.sessionData.isAdmin = false;
                console.log("logOut");
                console.log(this.http.sessionData.isLogged);
            }
            );

    }

    public getisLogged() {
        return this.http.sessionData.isLogged;
    }

    public getisAdmin() {
        return this.getisLogged() && this.http.sessionData.isAdmin;
    }

    public getUser() {
        return this.http.sessionData.userLogged;
    }

    private loginFailed(error: any){
      this.getisLogged();
      return Observable.throw("Server error (" + error.status + "): " + error.text())
    }
}