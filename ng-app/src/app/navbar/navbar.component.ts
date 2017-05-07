import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';
import 'rxjs/Rx';

import { Community } from "app/communities/community.model";

@Component({
    selector: 'navbar',
    templateUrl: './navbar.component.html'
})
export class NavbarComponent {
    criteria: string;

    constructor(private sessionService: LoginService, private router: Router) {
    }

    logOut() {
        this.sessionService.logOut().subscribe();
        this.router.navigate(['/']); 
    }
    search() {
        if (this.criteria != null) {
            this.router.navigateByUrl("/search/" + this.criteria)
        }
    }
}