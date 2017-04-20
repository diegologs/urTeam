import { Component, AfterViewChecked } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { CommunityService } from './communities.service';
import { Community } from './community.model';

import { News } from "app/news/news.model";
import { LoginService } from "app/login/login.service";
import { User } from "app/user/user.model";
import { UserService } from "../user/user.service";


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

  login: boolean;

  groupId: number;

  groupNumberOfFollowers: number;

  user: User;
  ownerId: User;
  isOwner: boolean;




  communityID: number;

  constructor(private router: Router, private userService: UserService, activatedRoute: ActivatedRoute, private service: CommunityService, private sessionService: LoginService) {
    this.login = false;
    let id = activatedRoute.snapshot.params['id'];
    this.communityID = id;
    this.groupId = id;
    this.getCommunity();
    this.groupUsers = [];
    this.community = { name: this.groupName, info: this.groupInfo, city: this.groupCity, main_photo: this.imgUrl, sport: this.groupSport, communityUsers: this.groupUsers };
    this.ownerId = {username:"",surname:"",nickname:"",email:"",country:""};
}

  getCommunity() {
    this.service.getGroup(this.communityID).subscribe(
      community => {
        this.community = community;
        this.groupUsers = community.communityUsers;
        this.user = this.sessionService.getUser();
        this.ownerId = community.owner_id;
        this.isOwner = (this.ownerId.nickname === this.user.nickname);
      },
      error => console.error(error),
      () => this.checkFollow()
    );
  }

  getUser() {
    this.userService.getUser(this.user.nickname).subscribe(
      user => { this.user = user; }
    )
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
    this.service.followGroup(this.communityID).subscribe(
      response => {
        this.groupUsers = response.communityUsers;
        this.getCommunity();
        this.getUser();
      },
      error => console.log(error),
    )
    // if (!this.checkFollowers()) {
    //   this.service.followGroup(this.communityID).subscribe(
    //     community => console.log(community),
    //     error => console.error(error),
    //   );
    // } else {
    //   this.unfollowGroup();
    // }
    // this.refresh();
  }

  unfollowGroup() {
    this.service.unfollowGroup(this.communityID).subscribe(
      response => {
        this.groupUsers = response.communityUsers;
        this.getCommunity();
        this.getUser();
      },
      error => console.log(error),
    )
  }

  checkFollow() {
    if (this.sessionService.getisLogged()) {
      let cosa: boolean = (this.community.communityUsers.find(
        user1 => user1.nickname == this.user.nickname) != undefined);
      return cosa;
    }
  }

  // this.groupNumberOfFollowers = this.community.communityUsers.length;
  // if (this.sessionService.getisLogged) {
  //   this.login = true;
  //   for (let follower of this.community.communityUsers) {
  //     if (follower) {
  //       //console.log(follower.nickname === this.sessionService.getUser().nickname);
  //       if (follower.nickname === this.sessionService.getUser().nickname) {
  //         this.isFollowing = "DEJAR DE SEGUIR";
  //         return true;
  //       }
  //     }
  //   }
  // }

refresh() {
  console.log(this.groupId);
  this.service.getGroup(this.groupId).subscribe(
    community => this.community = community,
    error => console.error(error),
    () => this.checkFollow()
  );
}

}