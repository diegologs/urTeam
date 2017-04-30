import { Component } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd ,NavigationStart} from '@angular/router';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';

import { Community } from "../communities/community.model";

import { SearchBoxService } from './searchbox.service';

@Component({
    selector: 'search',
    templateUrl: './searchbox.component.html',
    styleUrls: ['../events/events.component.css','../communities/communities.component.css', ], 
})
export class SearchBoxComponent {

    name: string;

    events: Event[];
    users: User[];
    communities: Community[];

    group: string;
    groups: string[];


    constructor(private sessionService: LoginService, private router: Router, activatedRoute: ActivatedRoute, private service: SearchBoxService) {
        this.groups = [];
        let criteria = activatedRoute.snapshot.params['criteria'];
        this.search(criteria);
        let supscription = router.events.subscribe((val) => {
            if(val instanceof NavigationStart && !val.url.startsWith('/search/')) supscription.unsubscribe();
            if (val instanceof NavigationEnd) this.search(val.url.slice(val.url.lastIndexOf('/') + 1));
        })
    }

    search(criteria: string) {
        this.service.searchUsers(criteria).subscribe(
            users => {
                this.users = users.content;
                this.users = users.slice();
            },
            error => console.error(error)
        )
        this.service.searchCommunities(criteria).subscribe(
            communities => {
                this.communities = communities.slice();
                for (let group of communities) {
                    this.groups.push(group.name);
                }
                this.groups = this.groups.slice();
            },
            error => console.error(error)
        )
        this.service.searchEvents(criteria).subscribe(
            events => {
                this.events = events.content;
                this.events = events.slice();
            },
            error => console.error(error)
        )
    }

}