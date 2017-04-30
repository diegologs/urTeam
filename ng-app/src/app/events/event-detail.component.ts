import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { EventService } from './events.service';
import { Event } from './events.model';
import { User } from '../user/user.model';
import { LoginService } from "../login/login.service";
import { UserService } from "../user/user.service";
import {PublicComponent} from '../public.component';

@Component({
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent {
  event: Event;
  info: "Cargando";
  participants_IDs: User[];
  user: User;
  ownerId: User;
  isOwner: boolean;
  eventID: number;

  constructor(private sessionService: LoginService, private userService: UserService, private router: Router, activatedRoute: ActivatedRoute, private service: EventService,private pubComponent: PublicComponent) {
    this.ownerId = {username:"",surname:"",nickname:"",email:"",country:""};
    this.eventID = activatedRoute.snapshot.params['id'];
    this.getEvent();
  }

  getEvent(){
    this.service.getEvent(this.eventID).subscribe(
      event => {
        this.event = event;
        this.ownerId = event.owner_id;
        this.participants_IDs = event.participants_IDs;
        this.user = this.sessionService.getUser();
        this.isOwner = (this.ownerId.nickname === this.user.nickname);
      },
      error => console.error(error),
      () => this.checkFollow()
    );
  }

  getUser(){
    this.userService.getUser(this.user.nickname).subscribe(
      user =>{ this.user = user;}
    )
  }

  follow() {
    this.service.followEvent(this.event.id).subscribe(
      response => {
        this.participants_IDs = response.participants_IDs;
        this.getEvent();
        this.getUser();
        this.pubComponent.msgs.push({severity:'success', summary:'Acción realizada'});
      },
      error => {
        console.log(error);
        this.pubComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un error al realizar acción'});},
    )
    
  }

  checkFollow() {
    if (this.sessionService.getisLogged()) {
      let cosa: boolean = (this.event.participants_IDs.find(
        user1 => user1.nickname == this.user.nickname) != undefined);
      return cosa;
    }
    
  }

  editInfo() {
    this.event.info = this.info;
    this.service.updatedEvent(this.event.id, this.event).subscribe(
      event => {
        this.pubComponent.msgs.push({severity:'success', summary:'Evento actualizado', detail:'Información actualizada satisfactoriamente'});
    },
      error => {
        this.pubComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un fallo durante la actualización del evento'});
      }
    );
  }
}