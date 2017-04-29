import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventService } from "app/events/events.service";
import { LoginService } from "app/login/login.service";
import { Community } from "app/communities/community.model";
import { CommunityService } from "app/communities/communities.service";
import {PublicComponent} from '../public.component';

@Component({
  selector: 'admin-events',
  templateUrl: './admin-communities.component.html',
  styleUrls: ['../communities/communities.component.css']
})
export default class AdminCommunities implements OnInit {
 
  communities: Community[];
  groupsPage: number;
  groupsPageActual: number;
  moreGroupsButtonText: string;

  communityID: number;

     imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router:Router, private service: CommunityService, private sessionService: LoginService,private pubComponent: PublicComponent){}

   ngOnInit() {
    this.groupsPage = 0;
    this.moreGroupsButtonText = "Ver Más";
    this.service.getGroups(this.groupsPage).subscribe(
      communities => {
        this.groupsPage++;
        this.communities = communities.content;
        this.groupsPageActual = communities.totalPages;
       
      },
      error => {
        console.log(error);
      }
    )
  }


   moreGroups() {
    if (this.groupsPage < this.groupsPageActual) {
      this.service.getGroups(this.groupsPage).subscribe(
        communities => {
          this.groupsPage++;
          this.communities = this.communities.concat(communities.content);
        },
        error => {
          console.log(error);
        }
      )
    }
    else {
      this.moreGroupsButtonText = 'No hay mas resultados';
    }
  }

  deleteCommunity(id: number){

    this.service.deleteGroup(id).subscribe(
      response => {
        this.getCommunitys();
        this.pubComponent.msgs.push({severity:'success', summary:'Comunidad eliminada', detail:'Comunidad eliminada satisfactoriamente'}); 
        
      },
      error => {
        console.log(error);
        this.pubComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un errror durante la eliminación de la comunidad'}); 
      }
    )
  }

  getCommunitys() {
   this.groupsPage = 0;
    this.moreGroupsButtonText = "Ver Más";
    this.service.getGroups(this.groupsPage).subscribe(
      communities => {
        this.groupsPage++;
        this.communities = communities.content;
        this.groupsPageActual = communities.totalPages;
       
      },
      error => {
        console.log(error);
      }
    )

  }
  
 
}

