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
export class CommunityDetailComponent implements AfterViewChecked {






  groupName = "Comunidad 0";
  groupInfo = "AAA"
  groupCity = "toledo"
  groupSport = "Running"
  groupUsers: User[] = new Array(1);

  imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  community: Community = { name: this.groupName, info: this.groupInfo, city: this.groupCity, main_photo: this.imgUrl, sport: this.groupSport, communityUsers: this.groupUsers }

  info: string;

  newsTitle: string;
  newsText: string;

  myService: CommunityService;

  isFollowing: boolean;

  login: boolean;





  communityID: number;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private service: CommunityService, private sessionService: LoginService) {
    let id = activatedRoute.snapshot.params['id'];
    this.communityID = id;
    service.getGroup(id).subscribe(
      community => this.community = community,
      error => console.error(error)

    );

    this.sessionService.userLogged = { generatedId: "user", username: "user", surname: "user", nickname: "user", email: "user", country: "spain" };

  }

  ngAfterViewChecked(): void {
    for (let follower of this.community.communityUsers) {
      console.log(follower);


      if (follower === this.sessionService.userLogged) {

        this.login = true;
        if (this.sessionService.isLogged) {

          this.isFollowing = true;
        }
      }
    }
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
      community => console.log(community),
      error => console.error(error)

    );

  }


  unfollowGroup() {
    this.service.followGroup(this.communityID).subscribe(
      community => console.log(community),
      error => console.error(error)

    );

  }



}