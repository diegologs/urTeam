import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { LoginService } from './login.service';
import { Http } from "@angular/http";

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  nickname: string;
  pass: string;


 constructor(private http: Http, private service:LoginService) {}

  

 

  logIn(){
    this.service.logIn(this.nickname ,this.pass).subscribe(
      user => console.log(user)
    );
   
  }
}