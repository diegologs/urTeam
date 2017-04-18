import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Event } from './events.model';
import { HttpClient } from '../HttpClient/httpClient';


const BASE_URL = 'https://localhost:8443/api/events/';

@Injectable()
export class EventService {

	constructor(private http: HttpClient) { }

	getEvents(page: number) {
		return this.http.get(BASE_URL + "?page=" + page +"&size=6")
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	getEvent(id: number | string) {
		return this.http.get(BASE_URL + id)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	getEventsBySport(sport: string){
		return this.http.get(BASE_URL + "sport/" + sport + "?page=0&size=3")
		.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	getEventMember(id: number | string) {
		return this.http.get(BASE_URL + id + '/members')
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	createEvent(event: Event) {
		return this.http.post(BASE_URL, event)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	deleteEvent(id: number | string) {
		return this.http.delete(BASE_URL + id)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	updatedEvent(id: number | string, event: Event){
		return this.http.put(BASE_URL + id,event)
            .map(response => response.json())
            .catch(error => this.handleError(error));
	}

	followEvent(id: number | string){
		return this.http.put(BASE_URL + id, null)
            .map(response => response.json())
            .catch(error => this.handleError(error));
	}

	getEventAvatar(id: number | string){
		return this.http.get(BASE_URL + id + '/avatar')
            .map(response => response.json())
            .catch(error => this.handleError(error));
	}


	private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}