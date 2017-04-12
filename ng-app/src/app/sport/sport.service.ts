import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import {Sport} from './sport.model';


const BASE_URL = 'https://localhost:8443/api/sports/';

@Injectable()
export class StatsService{

    constructor(private http: Http){}

    getSports() {
		return this.http.get(BASE_URL)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

    getSport(sportName: string) {
		return this.http.get(BASE_URL+sportName)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

    createSport(sport: Sport){
        return this.http.post(BASE_URL,sport)
        .map(response =>response.json())
        .catch(error => this.handleError(error));
    }

    updateSport(sportName: string, sport: Sport){
        return this.http.put(BASE_URL+sportName,sport)
        .map(response =>response.json())
        .catch(error => this.handleError(error));
    }

    deleteSport(sportName: string){
        return this.http.delete(BASE_URL+sportName)
        .map(response =>response.json())
        .catch(error => this.handleError(error));
    }

    private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}