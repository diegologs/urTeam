import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import { User } from './user.model';
import { Event } from '../events/events.model';
import { Community } from '../communities/community.model';
import { ChartsModule } from 'ng2-charts';

@Component({
  selector: 'user-profile',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;

pieChartOptions =  {
  chartType: 'PieChart',
  dataTable: [
    ['Task', 'Hours per Day'],
    ['Work',     11],
    ['Eat',      2],
    ['Commute',  2],
    ['Watch TV', 2],
    ['Sleep',    7]
  ],
  options: {'title': 'Tasks', 'pieHole': '0.4'},
};

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: UserService){
      let nickname = activatedRoute.snapshot.params['nickname'];
      service.getUser(nickname).subscribe(
          user => {
            this.user = user
          },
          error => console.error(error)
      );
  }


  ngOnInit(){

  }
}