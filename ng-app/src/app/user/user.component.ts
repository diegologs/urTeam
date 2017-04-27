import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import { User } from './user.model';
import { Event } from '../events/events.model';
import { Community } from '../communities/community.model';
import { StatsService } from '../stat/stats.service';
import { Stat } from '../stat/stat.model';
import { LoginService } from "../login/login.service";


@Component({
  selector: 'user-profile',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {

  user: User;
  level: number = 0;
  value: number = 0;
  date: string;
  sport: string;

  followers: User[];

  

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

  constructor(private router:Router, activatedRoute: ActivatedRoute, private service: UserService,
  private sessionService: LoginService 
  //private statService: StatsService
  ){
      let nickname = activatedRoute.snapshot.params['nickname'];
      service.getUser(nickname).subscribe(
          user => {
            this.user = user;
            this.followers = user.followers;

            this.value = (user.score % 1000 ) / 10;
            this.level = Math.floor(user.score /1000);
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

  OnInit(){

  }


  addStats(sport: string, date: string, sesionTime: number){
    let stat: Stat;
    stat= {date:date, totalSesionTime:sesionTime};
    this.sport = sport;
    //this.statService.createUserStats(this.user.nickname, this.sport, this.stat);
  }


  checkNotMe(){
    return (!(this.sessionService.getUser().nickname == this.user.nickname));
  }

  checkIsMyFriend(){
    let temp:boolean = false;
    for(let user of this.user.followers){
      if(user.id == this.sessionService.getUser().id){
        temp = true;
      }
    }
    return temp;
  }

  follow(){
    this.service.putUserFriends(this.user.nickname).subscribe(
      response => {
          this.getUserAgain();
      },
      error => console.log(error)
    )
  }

  getUserAgain(){
    this.service.getUser(this.user.nickname).subscribe(
          user => {
            this.user = user;
            this.followers = user.followers;

            this.value = (user.score % 1000 ) / 10;
            this.level = Math.floor(user.score /1000);
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

}