import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';


import { HttpClient } from '../HttpClient/httpClient';


const BASE_URL = 'https://localhost:8443/api/searchbox/';

@Injectable()
export class NavbarService {

	constructor(private http: HttpClient) { }

	searchAll(name: string){
    return this.http.get(BASE_URL + name)
			.map(response => response.json())
			.catch(error => this.handleError(error));


    }

    searchUsers(name: string){
    return this.http.get(BASE_URL + "users/" + name)
			.map(response => response.json())
			.catch(error => this.handleError(error));


    }


searchCommunities(name: string){
    return this.http.get(BASE_URL + "groups/" + name)
			.map(response => response.json())
			.catch(error => this.handleError(error));


    }

    searchEvents(name: string){
    return this.http.get(BASE_URL + "events/" + name)
			.map(response => response.json())
			.catch(error => this.handleError(error));


    }





	private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}