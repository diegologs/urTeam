import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';

import { NavbarService } from './navbar.service';
import { Community } from "app/communities/community.model";

@Component({
    selector: 'navbar',
    templateUrl: './navbar.component.html'
})
export class NavbarComponent {

    name: string;

    events: Event[];
    users: User[];
    communities: Community[];

    group: string;
    groups: string[];

    constructor(private sessionService: LoginService, private router: Router, private service: NavbarService) { 
        this.groups = [];
    }

    logOut() {
        this.sessionService.logOut().subscribe()
    }

    search() {


        this.service.searchUsers(this.name).subscribe(
            users => {

                this.users = users.content;
                console.log(users);
                this.users = users.slice();
            },
            error => console.error(error)
        )

        this.service.searchCommunities(this.name).subscribe(
            communities => {
                
                console.log(communities);
                this.communities = communities.slice();
                for (let group of communities){
                  this.groups.push(group.name);
                  
                }
                this.groups = this.groups.slice();
            },

            

            error => console.error(error)
        )


        this.service.searchEvents(this.name).subscribe(
            events => {
                this.events = events.content;
                console.log(events);
                 this.events = events.slice();
            },
            error => console.error(error)
        )
    }
}