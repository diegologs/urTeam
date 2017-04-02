package urteam.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class CustomUserDeserializer extends StdDeserializer<List<User>>{
	 
    public CustomUserDeserializer() {
        this(null);
    }
 
    public CustomUserDeserializer(Class<?> vc) {
        super(vc);
    }
 
    @Override
    public List<User> deserialize(
      JsonParser jsonparser, 
      DeserializationContext context) 
      throws IOException, JsonProcessingException {
         
        return new ArrayList<>();
    }
}
