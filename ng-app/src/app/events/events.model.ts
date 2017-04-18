import {User} from '../user/user.model';

export class Event {
    id?: number;
	eventId: string;
    constructor(
        public name: string,
        public sport: string,
        public price: number,
        public info: string,
        public place: string,
        public start_date: string,
        public end_date: string,
       
    ){}
    main_photo: string;
    eventImages: string[];
    day_date: string;
    month_date: string;
    year_date: string;
    owner_id: string;
    participants_IDs: User[];
}