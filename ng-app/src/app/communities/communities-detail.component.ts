import { Component, AfterViewChecked } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { CommunityService } from './communities.service';
import { Community } from './community.model';

import { News } from "app/news/news.model";
import { LoginService } from "app/login/login.service";
import { User } from "app/user/user.model";


@Component({
  templateUrl: './communities-detail.component.html',
  styleUrls: ['./communities-detail.component.css']
})
export class CommunityDetailComponent {






  groupName = "Comunidad 0";
  groupInfo = "AAA"
  groupCity = "toledo"
  groupSport = "Running"
  groupUsers: User[];

  imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  community: Community;

  info: string;

  newsTitle: string;
  newsText: string;

  myService: CommunityService;

  isFollowing: string;

  login: boolean;

  groupId: number;

  groupNumberOfFollowers: number;





  communityID: number;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private service: CommunityService, private sessionService: LoginService) {
    this.isFollowing = "SEGUIR";
    this.login = false;
    let id = activatedRoute.snapshot.params['id'];
    this.communityID = id;
    this.groupId = id
    service.getGroup(id).subscribe(
      
      community => this.community = community,
      error => console.error(error),
      () => this.checkFollowers()

    );

    this.groupUsers = [];
    this.community = { name: this.groupName, info: this.groupInfo, city: this.groupCity, main_photo: this.imgUrl, sport: this.groupSport, communityUsers: this.groupUsers };


  }

    
  



  editInfo() {


    this.community.info = this.info;
    this.service.updateGroup(this.communityID, this.community).subscribe(
      community => console.log(community),
      error => console.error(error)

    );



  }

  addNews() {

    let updateNews: News;

    updateNews = { title: this.newsTitle, text: this.newsText }

    this.service.addNews(this.communityID, updateNews).subscribe(
      community => console.log(community),
      error => console.error(error)

    );



  }

  followGroup() {
    
    if(!this.checkFollowers()){
    
      this.service.followGroup(this.communityID).subscribe(
        community => console.log(community),
        error => console.error(error),
      
      );

    }else{

      this.unfollowGroup();

    }

      this.refresh();

  



    

  }


  unfollowGroup() {
    this.service.unfollowGroup(this.communityID).subscribe(
      community => console.log(community),
      error => console.error(error)

    );

  }

  checkFollowers(){

    this.groupNumberOfFollowers = this.community.communityUsers.length;
     
   if (this.sessionService.getisLogged) {

     this.login = true;

     for (let follower of this.community.communityUsers) {

      if (follower) {

        //console.log(follower.nickname === this.sessionService.getUser().nickname);

      

        
          if (follower.nickname === this.sessionService.getUser().nickname) {



            
            this.isFollowing = "DEJAR DE SEGUIR";
            return true;
          }
        }
      }
    }

 
  }


  refresh(){
   
   console.log(this.groupId);
    this.service.getGroup(this.groupId).subscribe(
      
      community => this.community = community,
      error => console.error(error),
      () => this.checkFollowers()

    );
  }

}