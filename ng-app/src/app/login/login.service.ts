import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

const BASE_URL = 'https://127.0.0.1:8443/api/';

@Injectable()
export class LoginService{

    constructor(private http: Http){}

    logIn(){

    }
    logOut(){

    }
}