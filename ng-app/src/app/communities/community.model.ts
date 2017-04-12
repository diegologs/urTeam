import {User} from '../user/user.model';

export interface Community {
    id?: number;
	groupId: string;
	name: string;
    sport: string;
    info: string;
    country: string;
    city: string;
    main_photo: string;
    owner_id: string;
    communityUsers: User[];
}