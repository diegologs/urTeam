import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { routing ,appRoutingProviders} from './app.routing';
import {Ng2PageScrollModule} from 'ng2-page-scroll';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { SearchBoxService } from './searchbox/searchbox.service';
import { SearchBoxComponent } from './searchbox/searchbox.component';

import { EventsComponent } from './events/events.component';
import { EventService} from './events/events.service';
import { EventDetailComponent} from './events/event-detail.component';
import { EventCreateComponent } from './events/event-create.component';

import { CommunitiesComponent } from './communities/communities.component';
import { CommunitiesCreate } from './communities/communities-create.component';
import { CommunityDetailComponent } from './communities/communities-detail.component';
import { CommunityService} from './communities/communities.service';

import { UserComponent } from './user/user.component';
import { UserService } from './user/user.service';

import {PublicComponent} from './public.component';
import {PrivateComponent} from './admin/admin.component';
import AdminEvents from './admin/admin-events.component';
import AdminCommunities from './admin/admin-communities.component';
import AdminUsers from './admin/admin-users.component';

import { LoginComponent } from './login/login.component';
import { LoginService } from './login/login.service';
import { HttpClient } from "./HttpClient/httpClient";

import { Ng2GoogleChartsModule } from 'ng2-google-charts';
import { ProgressBarModule } from 'primeng/primeng';




@NgModule({
  declarations: [
    AppComponent, NavbarComponent, FooterComponent, HomeComponent, EventsComponent, EventDetailComponent,
    CommunitiesComponent,CommunityDetailComponent,CommunitiesCreate, UserComponent, LoginComponent,EventCreateComponent,
    PrivateComponent,AdminEvents,AdminCommunities,AdminUsers,PublicComponent,SearchBoxComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    NgbModule.forRoot(),
    routing,
    Ng2PageScrollModule.forRoot(),
    Ng2GoogleChartsModule,
    ProgressBarModule

  ],
  providers: [EventService, CommunityService , LoginService, UserService,HttpClient,appRoutingProviders, SearchBoxService],
  bootstrap: [AppComponent]
})
export class AppModule { }
