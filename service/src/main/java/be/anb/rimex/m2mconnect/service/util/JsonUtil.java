package be.anb.rimex.m2mconnect.service.util;

import be.anb.rimex.m2mconnect.service.request.AuthentificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static String convertObjectToJson(Object object) throws Exception{
			
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		
	}
	
	public static <T> T convertJsonStringToObject(String jsonString, Class<T> clazz) throws Exception{
		
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				
				// Convertir la chaîne JSON en objet de la classe spécifiée (clazz)
				return objectMapper.readValue(jsonString, clazz);
			}catch (Exception e){
				e.getMessage();
				return null;
			}
		
	}
	
}
