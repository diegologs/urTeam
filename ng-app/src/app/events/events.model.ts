import {User} from '../user/user.model';

export interface Event {
    id?: number;
	eventId: string;
	name: string;
    sport: string;
    price: number;
    info: string;
    main_photo: string;
    place: string
    eventImages: string[];
    start_date: string;
    end_date: string;
    day_date: string;
    month_date: string;
    year_date: string;
    owner_id: string;
    participants_IDs: User[];
}