import { Community } from '../communities/community.model';
import { UserSportStats } from '../stat/userSportsStats.model';

export interface User {
    id?: number;
	generatedId?: string;
	username: string;
    surname: string;
    nickname: string;
    email: string;
    bio?: string;
    city?: string;
    country: string;
    score?: number;
    roles?: string[];
    following?: User[];
    followers?: User[];
    communityList?: Community[];
    eventList?: Event[];
    passwordHash?: string;
    sportStats?: UserSportStats;
}