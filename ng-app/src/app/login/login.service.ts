import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Http, Headers } from '@angular/http';

import { User } from '../user/user.model';
import { UserService } from '../user/user.service';

import 'rxjs/Rx';

@Injectable()
export class LoginService {

    private isLogged:boolean = false;  
    private credentials:string; 
    private userLogged :User; 

    constructor(private router: Router, private http: Http, private userService:UserService) { }

  

    private handleLogIn(response){
        this.isLogged = true;
        this.userService.getUser(response.json().id).subscribe(
            user => this.userLogged = user
        );
    }
    
    logIn(nickname: string, pass: string) {
        let headers = new Headers();
        this.credentials = btoa(nickname + ':' + pass);
        headers.append('Authorization', 'Basic ' + this.credentials);
        return this.http.get("https://localhost:8443/api/login", {headers : headers})
            .map(response => {
                this.handleLogIn(response);
                localStorage.setItem("user", response.json());
                console.log(this.credentials);
                return response.json();
            })
            .catch(error => this.handleError(error))
    }

    logOut(){
        let headers = new Headers();
        headers.append('Authorization', 'Basic ' + this.credentials);
        console.log(this.credentials);
        return this.http.get("https://localhost:8443/api/logout", {headers:headers})
        .map(response =>{
            this.isLogged = false;
            localStorage.removeItem("user");
            return true;
        })
        .catch (error => this.handleError(error))
    }
 
 
     getCredentials(){
        return this.credentials;
    }

    setCredentials(credentials:string){
        this.credentials = credentials;
    }

    isUserLogged(){//
        return this.isLogged;
    }


    private handleError(error: any) {
        console.log(error);
        return Observable.throw("Server error (" + error.status + "): " + error.text())
    }
}