import { Component } from '@angular/core';
import { Router} from '@angular/router';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';
import {Message} from 'primeng/primeng';

@Component({
    selector: 'admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class PrivateComponent {
    
    public msgs: Message[] = [];

    constructor(private sessionService: LoginService, private router: Router) {
        this.msgs.push({severity:'infor', summary:'¡¡Eres Administrador!!', detail:'Recuerda... un gran poder conlleva una gran responsabilidad!'});
    }
}