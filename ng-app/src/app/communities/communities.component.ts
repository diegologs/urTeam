import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommunityService } from './communities.service';
import { Community } from "app/communities/community.model";
import { LoginService } from "app/login/login.service";

@Component({
  selector: 'communities',
  templateUrl: './communities.component.html',
   styleUrls: ['./communities.component.css']
})

export class CommunitiesComponent implements OnInit {
   
  communities: Community[];
  groupsPage: number;
  groupsPageActual: number;
  moreGroupsButtonText: string;

   imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router:Router, private service: CommunityService, private sessionService: LoginService){}

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

 
}

