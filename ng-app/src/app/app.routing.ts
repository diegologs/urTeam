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

import { PublicComponent } from './public.component';
import { ComponentTwo } from './admin/component-two';
import ChildOne from './admin/child-one';
import ChildTwo from './admin/child-two';



// const appRoutes = [
//     { path: 'home', component: HomeComponent },
//     { path: 'events', component: EventsComponent },
//     { path: 'events/create', component: EventCreateComponent },
//     { path: 'events/:id', component: EventDetailComponent },
//     { path: 'communities', component: CommunitiesComponent },
//     { path: 'communities/create', component: CommunitiesCreate },
//     { path: 'communities/:id', component: CommunityDetailComponent },
//     { path: 'login', component: LoginComponent },
//     { path: 'users/:nickname', component: UserComponent },
//     { path: '', redirectTo: 'home', pathMatch: 'full' }
// ]

const appRoutes: Routes = [
    { path: '', redirectTo: 'public', pathMatch: 'full' },
    {
        path: 'public', component: PublicComponent,
        children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', component: HomeComponent },
            { path: 'events', component: EventsComponent },
            { path: 'events/create', component: EventCreateComponent },
            { path: 'events/:id', component: EventDetailComponent },
            { path: 'communities', component: CommunitiesComponent },
            { path: 'communities/create', component: CommunitiesCreate },
            { path: 'communities/:id', component: CommunityDetailComponent },
            { path: 'login', component: LoginComponent },
            { path: 'users/:nickname', component: UserComponent }
        ]
    },
    {
        path: 'admin', component: ComponentTwo,
        children: [
            { path: '', redirectTo: 'child-one', pathMatch: 'full' },
            { path: 'child-one', component: ChildOne },
            { path: 'child-two', component: ChildTwo }
        ]
    }
]

export const appRoutingProviders: any[] = []
export const routing = RouterModule.forRoot(appRoutes);