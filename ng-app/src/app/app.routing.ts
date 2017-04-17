import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';

import { EventsComponent } from './events/events.component';
import { EventDetailComponent } from './events/event-detail.component';
import { EventCreateComponent } from './events/event-create.component';


import { CommunitiesComponent } from './communities/communities.component';
import { CommunityDetailComponent } from './communities/communities-detail.component';

import { UserComponent } from './user/user.component';

import { LoginComponent } from './login/login.component';
import { CommunitiesCreate } from "app/communities/communities-create.component";

const appRoutes = [
    { path: 'home', component: HomeComponent },
    { path: 'events', component: EventsComponent },
    { path: 'events/:id', component: EventDetailComponent },
    { path: 'create', component: EventCreateComponent },
    { path: 'communities', component: CommunitiesComponent },
    { path: 'communities/create', component: CommunitiesCreate },
    { path: 'communities/:id', component: CommunityDetailComponent },
    { path: 'login', component: LoginComponent },
    { path: 'users/:nickname', component: UserComponent },
    { path: '', redirectTo: 'home', pathMatch: 'full' }
]
export const  routing = RouterModule.forRoot(appRoutes);