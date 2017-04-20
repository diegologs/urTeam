import { User } from '../user/user.model';

export interface Event {
    id?: number;
    eventId?: string;
    name: string;
    sport: string;
    price: number;
    info: string;
    place: string;
    start_date: string;
    end_date: string;
    main_photo?: string;
    eventImages?: string[];
    day_date?: string;
    month_date?: string;
    year_date?: string;
    owner_id?: User;
    participants_IDs?: User[];
}