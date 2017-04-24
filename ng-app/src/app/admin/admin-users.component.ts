import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from "app/login/login.service";
import { User } from "app/user/user.model";
import { UserService } from "app/user/user.service";

@Component({
  selector: 'admin-users',
  templateUrl: './admin-users.component.html'
})
export default class AdminUsers implements OnInit {
 
  users: User[];

     imgUrl = "https://localhost:8443/image/event-avatar/aleatorio/default-mainphoto";

  constructor(private router:Router, private service: UserService, private sessionService: LoginService){}

   ngOnInit() {
    this.service.getUsers().subscribe(
      usersApi => {
        this.users = usersApi;
      },
      error => {
        console.log(error);
      }
    )
  }
}