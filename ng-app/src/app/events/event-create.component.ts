import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { EventService } from './events.service';
import { Event } from './events.model';
import { LoginService } from '../login/login.service';
import {PublicComponent} from '../public.component';

@Component({
  templateUrl: './event-create.component.html',
  styleUrls:['./event-create.component.css']
})
export class EventCreateComponent{

  name:string;
  place:string;
  price:number;
  start_date:string;
  end_date:string;
  sport:string;
  info:string;

  eventImage: any;

  event: Event;

  imgUrl = "";


  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: EventService,private sessionService: LoginService,private pubComponent: PublicComponent){}

  eventcreate(){
    let event: Event;
    event = {name: this.name, place: this.place, price:this.price,start_date:this.start_date,end_date:this.end_date,sport:this.sport,info:this.info,main_photo:this.imgUrl};
    this.service.createEvent(event).subscribe(
      
            event =>{ this.event = event;
            if(this.eventImage != null){
                    this.updatePhoto(event.id); 
                }else{
                    console.log("no hay foto");
                } 
            this.pubComponent.msgs.push({severity:'success', summary:'Evento creado', detail:'Nuevo evento creado satisfactoriamente'});
            this.router.navigate(['/']); 
          },
            error => {
            console.error(error);
            this.pubComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un fallo durante la creación del evento'});
            }
           
    );
  }

  updatePhoto(id: number){
         let formData = new FormData();
        formData.append('file', this.eventImage, this.eventImage.name);

        this.service.setPhoto(id, formData).subscribe(
            event => this.event = event,
            error => console.error(error)
        )
    }

  selectFile($event) {
    this.eventImage = $event.target.files[0];
    //console.log("Selected file: " + this.eventImage.name + " type:" + this.eventImage.type + " size:" + this.eventImage.size);
  }
}