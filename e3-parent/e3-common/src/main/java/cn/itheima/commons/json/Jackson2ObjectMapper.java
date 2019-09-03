package cn.itheima.commons.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Jackson2ObjectMapper extends ObjectMapper {

	  private static final long serialVersionUID = -4914439720976509928L;
	  
	  public Jackson2ObjectMapper()
	  {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    setDateFormat(format);
	    
	    getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
	    {
	      public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
	        throws IOException, JsonProcessingException
	      {
	        jg.writeString("");
	      }
	    });
	  }
}
