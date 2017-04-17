import { User } from '../user/user.model';
import { News } from "app/news/news.model";

export interface Community {
    id?: number;
	groupId?: string;
	name: string;
    sport: string;
    info: string;
    country?: string;
    city: string;
    main_photo: string;
    owner_id?: User;
    communityUsers?: User[];
    news?: News[];
}

