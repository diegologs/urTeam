import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { HttpClient } from '../HttpClient/httpClient';
import {User} from './user.model';


const BASE_URL = 'https://localhost:8443/api/users/';

@Injectable()
export class UserService{

	user: User;
	authCreds: string;

    constructor(private http: HttpClient){}

	setAuthHeaders(authCreds: string) {
    this.authCreds = authCreds;
	}

    getUsers() {
		return this.http.get(BASE_URL)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

    getUser(nickname: string) {
		return this.http.get(BASE_URL + nickname)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	createUser(user: User){
		return this.http.post(BASE_URL,user)
		.map(response => response.json())
		.catch(error => this.handleError(error));
	}

	updateUser(userName :string, user :User){
		return this.http.put(BASE_URL+userName,user)
		.map(response => response.json())
		.catch(error => this.handleError(error));
	}

	getUserAvatar(userName :string){
		return this.http.get(BASE_URL+userName+'/avatar')
		.map(response => response.json())
		.catch(error => this.handleError(error));
	}

	//updated user avatar

	getUserFriends(userName :string){
		return this.http.get(BASE_URL+userName+'/friends')
		.map(response => response.json())
		.catch(error => this.handleError(error));
	}

	getUserFollowers(userName :string){
		return this.http.get(BASE_URL+userName+'/followers')
		.map(response => response.json())
		.catch(error => this.handleError(error));
	}

    private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}