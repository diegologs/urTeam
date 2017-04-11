import { Stat } from "app/stat/stat.model";

export interface UserSportStats {
    id?: number;
	sportTotalTime: number;
	stats: Stat[]; 
}