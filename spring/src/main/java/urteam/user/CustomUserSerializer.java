package urteam.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomUserSerializer extends StdSerializer<List<User>>{

	public CustomUserSerializer() {
        this(null);
    }
 
    public CustomUserSerializer(Class<List<User>> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      List<User> users, 
      JsonGenerator generator, 
      SerializerProvider provider) 
      throws IOException, JsonProcessingException {
    	
    	class Follower{
    		public long id;
    		public String nickname;
    		public String username;
    		public String surname;
    		public String generatedId;
    	}
         
        List<Follower> following = new ArrayList<>();
        for (User user : users) {
        	Follower follower = new Follower();
        	follower.id = user.getId();
        	follower.nickname = user.getNickname();
            follower.username = user.getUserName();
            follower.surname = user.getSurname();
            follower.generatedId = user.getGeneratedId();
            following.add(follower);
        }
        generator.writeObject(following);
    }

}
