import {User} from '../user/user.model'
export interface SessionData{

  isLogged:boolean;
  isAdmin:boolean;
  userLogged: User;
  authToken: string;
}