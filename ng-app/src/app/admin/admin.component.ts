import { Component } from '@angular/core';
import { Router} from '@angular/router';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';

@Component({
    selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class PrivateComponent {

    constructor(private sessionService: LoginService, private router: Router) {}
}