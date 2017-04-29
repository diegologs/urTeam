import { Injectable, OnInit } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { LoginService } from "../login/login.service";
import { SessionData } from "../login/sessionData.model";

@Injectable()
export class HttpClient {

  public sessionData: SessionData;

  constructor(private http: Http) {
    this.sessionData = {
      isLogged: false, isAdmin: false, userLogged: {
        id: 99,
        generatedId: "",
        username: "",
        surname: "",
        nickname: "",
        email: "",
        bio: "",
        city: "",
        country: "",
        score: 0,
        roles: [],
        following: [],
        followers: [],
        communityList: [],
        eventList: [],
      },
      authToken: ""
    };
  }

  generateHeaders() {
    let headers = new Headers();
    if (this.sessionData.isLogged) 
      headers.append('Authorization', this.sessionData.authToken);
    return headers;
  }

  get(url) {
    return this.http.get(url, {
      headers: this.generateHeaders()
    });
  }

  post(url, data) {
    return this.http.post(url, data, {
      headers: this.generateHeaders()
    });
  }

  put(url, data) {
    return this.http.put(url, data, {
      headers: this.generateHeaders()
    });
  }

  delete(url) {
    return this.http.delete(url, {
      headers: this.generateHeaders()
    });
  }
}
