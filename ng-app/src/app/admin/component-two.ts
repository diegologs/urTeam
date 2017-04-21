import { Component } from '@angular/core';
import { Router} from '@angular/router';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';

@Component({
  selector: 'component-two',
  template: `
    <p>Component Two</p>
    
    <nav>
      <a [routerLink]="['child-one']">Child One</a>
      <a [routerLink]="['child-two']">Child Two</a>
    </nav>
    
    <div style="color: red; margin-top: 1rem;">
      Component Two's router outlet:
    </div>
    <div style="border: 2px solid red; padding: 1rem;">
      <router-outlet></router-outlet>
    </div>
  `,
})
export class ComponentTwo {

    constructor(private sessionService: LoginService, private router: Router) {}
}