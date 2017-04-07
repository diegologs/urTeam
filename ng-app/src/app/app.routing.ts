import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { EventsComponent } from './events/events.component';
import { CommunitiesComponent } from './communities/communities.component';

const appRoutes = [
    { path: 'home', component: HomeComponent },
    { path: 'events', component: EventsComponent },
    { path: 'communities', component: CommunitiesComponent },
    { path: '', redirectTo: 'home', pathMatch: 'full' }
]
export const  routing = RouterModule.forRoot(appRoutes);