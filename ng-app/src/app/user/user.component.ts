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
    ['Task', 'Hours per Day']
  ],
  options: {
    'title': 'Porcentaje mensual',
     'pieHole': '0.4',
     'backgroundColor':'transparent',
     'legend':{'position': 'right', 'textStyle': {'color': 'white', 'fontSize': '16'}},
     'titleTextStyle':{'color':'white','fontSize':'24'}
    },
};

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: UserService){
      let nickname = activatedRoute.snapshot.params['nickname'];
      service.getUser(nickname).subscribe(
          user => {
            this.user = user;
            if(this.user.sportStats['Running'] != null){
              this.pieChartOptions.dataTable.push(['Running',this.user.sportStats['Running'].sportTotalTime]);
            }
            if(this.user.sportStats['Mountain Bike'] != null){
              this.pieChartOptions.dataTable.push(['Mountain Bike',this.user.sportStats['Mountain Bike'].sportTotalTime]);
            }
            if(this.user.sportStats['Roller'] != null){
              this.pieChartOptions.dataTable.push(['Roller',this.user.sportStats['Roller'].sportTotalTime]);
            }
          },
          error => console.error(error)
      );
  }


  ngOnInit(){

  }
}