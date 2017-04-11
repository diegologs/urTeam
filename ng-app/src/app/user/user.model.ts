export interface User {
    id?: number;
	generatedId: string;
	username: string;
    surname: string;
    nickname: string;
    email: string;
    bio: string;
    city: string;
    country: string;
    socre: number;
    roles: string[];
    following: User[];
    followers: User[];
    //communityList: Community[];
    eventList: Event[];
}