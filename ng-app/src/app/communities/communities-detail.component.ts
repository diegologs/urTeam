import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { CommunityService } from './communities.service';
import { Community } from './community.model';

import { News } from "app/news/news.model";


@Component({
  templateUrl: './communities-detail.component.html',
  styleUrls: ['./communities-detail.component.css']
})
export class CommunityDetailComponent{

 
  community: Community;

  info: string;

  newsTitle: string;
  newsText: string;

  myService: CommunityService;

  
  
  communityID: number;
  imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: CommunityService){
      let id = activatedRoute.snapshot.params['id'];
      this.communityID = id;
      service.getGroup(id).subscribe(
          community => this.community = community,
          error => console.error(error)
      );
  }

  editInfo(){
        
    
     this.community.info = this.info;
     this.service.updateGroup(this.communityID, this.community).subscribe(
          community => console.log(community),
          error => console.error(error)

     );

      
      
  }

  addNews(){
    
    let updateNews: News;

    updateNews = {title: this.newsTitle, text:this.newsText}
    
     this.service.addNews(this.communityID, updateNews).subscribe(
          community => console.log(community),
          error => console.error(error)

     );

      
      
  }

  followGroup(){
     this.service.followGroup(this.communityID).subscribe(
          community => console.log(community),
          error => console.error(error)

     );

  }



}