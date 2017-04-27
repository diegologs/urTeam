import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import { User } from './user.model';
import { Event } from '../events/events.model';
import { Community } from '../communities/community.model';
import { StatsService } from '../stat/stats.service';
import { Stat } from '../stat/stat.model';
import { UserSportStats } from '../stat/userSportsStats.model';


@Component({
  selector: 'user-profile',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user: User;
  level: number = 0;
  value: number = 0;
  date: string;
  sport: string;

  stats: UserSportStats[];
  sport1: Stat[];
  sport2: Stat[];
  sport3: Stat[];
  cols: any[];

  formData: Stat = {
    totalSesionTime: 0,
	  date: '' 
  }
  
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

  constructor(private router:Router, private activatedRoute: ActivatedRoute, private service: UserService, private statService: StatsService){ }

  addStats(){ 
    this.statService.createUserStats(this.user.nickname, this.sport, this.formData).subscribe(
      response => {
        this.pieChartOptions.dataTable.splice(1,3);
        this.getUser();
      },
      error => console.error(error)
    );
  }

   getUser() {     
    this.service.getUser(this.user.nickname).subscribe(
      user => {
         this.user = user;
            this.value = (user.score % 1000 ) / 10;
            this.level = Math.floor(user.score /1000);
            this.pieChartOptions = Object.create(this.pieChartOptions);
            if(this.user.sportStats['Running'] != null){
              this.pieChartOptions.dataTable.push(['Running',this.user.sportStats['Running'].sportTotalTime]);
            }
            if(this.user.sportStats['Mountain Bike'] != null){
              this.pieChartOptions.dataTable.push(['Mountain Bike',this.user.sportStats['Mountain Bike'].sportTotalTime]);
            }
            if(this.user.sportStats['Roller'] != null){
              this.pieChartOptions.dataTable.push(['Roller',this.user.sportStats['Roller'].sportTotalTime]);
            }
         })
  }


  ngOnInit(){    
      let nickname = this.activatedRoute.snapshot.params['nickname'];
      this.service.getUser(nickname).subscribe(
          user => {
            this.user = user;
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

      this.statService.getUserStats(nickname).subscribe(
        stats => {
          console.log(stats);
          this.stats = stats;
          this.sport1 = stats['Running'].stats;
          this.sport2 = stats['Roller'].stats;
          this.sport3 = stats['Mountain Bike'].stats;

          this.cols = [
            {field: 'date', header: 'Fecha'},
            {field: 'totalSesionTime', header: 'Duracion'}
          ];
        }
        );
      
    }
}