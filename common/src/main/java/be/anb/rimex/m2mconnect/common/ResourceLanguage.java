package be.anb.rimex.m2mconnect.common;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceLanguage {
	
	private static ResourceLanguage instance;
	private ResourceBundle resourceBundle;
	
	private ResourceLanguage() {
		// Private constructor to prevent instantiation from outside
	}
	
	public static ResourceLanguage getInstance() {
		if (instance == null) {
			synchronized (ResourceLanguage.class) {
				if (instance == null) {
					instance = new ResourceLanguage();
				}
			}
		}
		return instance;
	}
	
	public ResourceBundle getResourceForLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle("m2mconnect", locale);
		return resourceBundle;
	}
	
	public String getTranslation(String key) {
		return resourceBundle.getString(key);
	}
}
