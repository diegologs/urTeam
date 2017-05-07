import { Component, OnInit,Directive,Input,Pipe} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import { User } from './user.model';
import { Event } from '../events/events.model';
import { Community } from '../communities/community.model';
import { StatsService } from '../stat/stats.service';
import { Stat } from '../stat/stat.model';
import { UserSportStats } from '../stat/userSportsStats.model';
import { LoginService } from "../login/login.service";
import {PublicComponent} from '../public.component';


@Component({
  selector: 'user-profile',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})

export class UserComponent implements OnInit{
  

  user: User;
  editedUser: User;
  level: number = 0;
  value: number = 0;
  date: string;
  sport: string;

  stats: UserSportStats[];
  sport1: Stat[];
  sport2: Stat[];
  sport3: Stat[];
  cols: any[];

  followers: User[];
  userImage: any;

  formData: Stat = {
    totalSesionTime: 0,
	  date: '' 
  };

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


  imgurl:string = '';


  constructor(private router:Router, private activatedRoute: ActivatedRoute, private service: UserService, private statService: StatsService, private sessionService: LoginService,private pubComponent: PublicComponent){ }

  ngOnInit(){    
      let nickname = this.activatedRoute.snapshot.params['nickname'];
      this.service.getUser(nickname).subscribe(
          user => {
            this.user = user;
            this.imgurl = 'https://localhost:8443/api/users/'+ user.nickname +'/avatar';
            this.editedUser = Object.assign({},user);
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

      this.statService.getUserStats(nickname).subscribe(
        stats => {          
          this.stats = stats;
          if(this.stats.length != 0){
            this.sport1 = stats['Running'].stats;
            this.sport2 = stats['Roller'].stats;
            this.sport3 = stats['Mountain Bike'].stats;
          }

          this.cols = [
            {field: 'date', header: 'Fecha'},
            {field: 'totalSesionTime', header: 'Duracion'}
          ];
        }
        );
  }

  getUser() {     
    this.service.getUser(this.user.nickname).subscribe(
      user => {
         this.user = user;
         this.editedUser = Object.assign({},user);
         this.followers = user.followers;
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
    });
    this.statService.getUserStats(this.user.nickname).subscribe(
      stats => {
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

  addStats(){ 
    this.statService.createUserStats(this.user.nickname, this.sport, this.formData).subscribe(
      response => {
        this.pieChartOptions.dataTable.splice(1,3);
        this.getUser();
        this.pubComponent.msgs.push({severity:'success', summary:'Nuevas estadisticas', detail:'Estadisticas añadidas satisfactoriamente'});
      },
      error => {
        console.error(error);
        this.pubComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un fallo al añadir estadisticas'});
      }
    );
  }
      
  checkNotMe(){
    if(this.sessionService.getUser()){
       return (!(this.sessionService.getUser().nickname == this.user.nickname));
    }
    return true;
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
          this.getUser();
          this.pubComponent.msgs.push({severity:'success', summary:'Acción realizada'});
      },
      error => {
        console.log(error) ;
        this.pubComponent.msgs.push({severity:'error', summary:'Error', detail:'Se ha producido un error al realizar acción'});
      }
    )
  }

  editUser(){
    this.service.updateUser(this.editedUser.nickname, this.editedUser).subscribe(
      response =>{
        console.log("Usuario editado.");
        this.pubComponent.msgs.push({severity:'success', summary:'Información actualizada', detail:'Información actualizada satisfactoriamente'});
        this.user.bio = response.bio;
        this.user.username = response.username;
        this.user.surname = response.surname;
        this.user.city = response.city;
        this.user.country = response.country;
        this.user.email = response.email;
        this.user.generatedId = response.generatedId;
        this.updatePhoto(response.nickname);
        this.router.navigate(['/']); 
        //this.sessionService.logIn(this.user.nickname,this.user.passwordHash);
      }
    )
  }

  updatePhoto(nickname: string){
         let formData2 = new FormData();
        formData2.append('file', this.userImage, this.userImage.name);
        this.service.setAvatar(nickname, formData2).subscribe(
            response => {
              this.pieChartOptions.dataTable.splice(1,3);
            },
            error => console.error(error)
        )
    }

 selectFile($event) {
    this.userImage = $event.target.files[0];
    //console.log("Selected file: " + this.groupImage.name + " type:" + this.groupImage.type + " size:" + this.groupImage.size);
  }

  errorHandler(event) {
    this.imgurl = 'http://icons.iconarchive.com/icons/blackvariant/button-ui-system-folders-alt/512/Group-icon.png';
  }


}