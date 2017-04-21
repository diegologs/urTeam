import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Community } from './community.model';
import { News } from "app/news/news.model";

import { HttpClient } from '../HttpClient/httpClient';


const BASE_URL = 'https://localhost:8443/api/groups/';

@Injectable()
export class CommunityService {

	constructor(private http: HttpClient) { }

	
	getGroups(page: number) {
		return this.http.get(BASE_URL + "?page=" + page +"&size=3")
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	getGroup(id: number | string) {
		return this.http.get(BASE_URL + id)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	

	createGroup(community: Community) {
		return this.http.post(BASE_URL, community)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	deleteGroup(id: number | string) {
		return this.http.delete(BASE_URL + id)
			.map(response => response.json())
			.catch(error => this.handleError(error));
	}

	updateGroup(id: number | string, community: Community){
		return this.http.put(BASE_URL + id,community)
            .map(response => response.json())
            .catch(error => this.handleError(error));
	}

	followGroup(id: number | string){
		return this.http.put(BASE_URL + id + '/followers', null)
            .map(response => response.json())
            .catch(error => this.handleError(error));
	}

	unfollowGroup(id: number | string){
		return this.http.delete(BASE_URL + id + '/followers')
            .map(response => response.json())
            .catch(error => this.handleError(error));
	}

	addNews(id: number | string, news: News){
		return this.http.post(BASE_URL + id + '/news', news)
			.map(response => response.json())
			.catch(error => this.handleError(error));

	}

	addImage(id: number | string, formData: FormData){
		return this.http.put(BASE_URL + id + '/image', formData)
		 .map(response => response.json())
         .catch(error => this.handleError(error));
	}

	

	
	private handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}
}