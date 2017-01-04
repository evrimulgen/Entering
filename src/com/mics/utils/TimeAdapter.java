package com.mics.utils;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class TimeAdapter implements JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		if(arg0 == null){  
            return null;  
        } else {  
            try {  
                return new Date(arg0.getAsLong());  
            } catch (Exception e) {  
                return null;  
            }  
        }  
	}

}
