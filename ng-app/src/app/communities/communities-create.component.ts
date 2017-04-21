import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CommunityService } from './communities.service';
import { Community } from "app/communities/community.model";
import { User } from "app/user/user.model";
import { LoginService } from "app/login/login.service";



@Component({
    templateUrl: './communities-create.component.html',
    styleUrls: ['./communities-create.component.css']
})

export class CommunitiesCreate {

    groupName: string;
    groupInfo: string;
    groupSport: string;
    groupCity: string;
    community: Community;

    groupImage: any;

    private userLogged:User;
    


    imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

    constructor(private router: Router, private service: CommunityService) { }

    createCommunity() {
        
        
        let group: Community;
        group = { name: this.groupName, info: this.groupInfo, city: this.groupCity, main_photo: this.imgUrl, sport: this.groupSport}

        this.service.createGroup(group).subscribe(
            community => this.community = community,
            error => console.error(error),
            
        );

        this.router.navigateByUrl('communities');

    }

    updatePhoto(group: Community){
         let formData = new FormData();
        formData.append('file', this.groupImage, this.groupImage.name);

        this.service.setPhoto(group.id, formData).subscribe(
            Community => this.community = Community,
            error => console.error(error)
        )
    }

 selectFile($event) {
    this.groupImage = $event.target.files[0];
    console.log("Selected file: " + this.groupImage.name + " type:" + this.groupImage.type + " size:" + this.groupImage.size);
  }






}

