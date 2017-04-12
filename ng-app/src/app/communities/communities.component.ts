import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommunityService } from './communities.service';
import { Community } from "app/communities/community.model";

@Component({
  selector: 'communities',
  templateUrl: './communities.component.html',
   styleUrls: ['./communities.component.css']
})

export class CommunitiesComponent implements OnInit {
   
  communities: Community[];
  constructor(private router:Router, private service: CommunityService){}

  ngOnInit(){
    this.service.getGroups().subscribe(
        communities => this.communities = communities.content,
        error => console.log(error)
    )
  }

 
}

