package be.anb.rimex.m2mconnect.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppLoginProperties {
	
	private static final Logger logger = LogManager.getLogger(AppLoginProperties.class);
	private static AppLoginProperties instance;
	private Properties ps;
	
	
	
	private AppLoginProperties() {
	}
	
	public static AppLoginProperties getInstance() {
		if (instance == null) {
			synchronized (AppProperties.class) {
				if (instance == null) {
					instance = new AppLoginProperties();
					instance.loadPropertiesLogin();
				}
			}
		}
		return instance;
	}
	
	private void loadPropertiesLogin() {
		try (InputStream input = new FileInputStream( EProperty.FILE_PATH_LOGIN.getName())) {
			Properties properties = new Properties();
			properties.load(input);
			ps = properties;
		}catch (IOException e) {
			logger.error("Impossible de trouver le fichier de propriétés");
		}
	}
	
	public String getEmail() {
		return ps.getProperty(EProperty.EMAIL_KEY.getName(), "");
	}
	
	public void setEmail(String email) {
		ps.setProperty(EProperty.EMAIL_KEY.getName(), email);
		savePreferences();
	}
	
	public void setTheme(String theme) {
		ps.setProperty(EProperty.THEME.getName(), theme);
		savePreferences();
	}
	
	public String getTheme() {
		return ps.getProperty(EProperty.THEME.getName(), "light");
	}
	
	public void setLanguage(String language) {
		ps.setProperty(EProperty.LANGUAGE.getName(), language);
		savePreferences();
	}
	
	public String getLanguage() {
		return ps.getProperty(EProperty.LANGUAGE.getName(), "fr");
	}
	
	
	public String getPassword() {
		return ps.getProperty(EProperty.PASSWORD_KEY.getName(), "");
	}
	
	public void setPassword(String password) {
		ps.setProperty(EProperty.PASSWORD_KEY.getName(), password);
		savePreferences();
	}
	
	public boolean getRememberMe() {
		return Boolean.parseBoolean(ps.getProperty(EProperty.REMEMBER_ME_KEY.getName(), "false"));
	}
	
	public void setRememberMe(boolean rememberMe) {
		ps.setProperty(EProperty.REMEMBER_ME_KEY.getName(), String.valueOf(rememberMe));
		savePreferences();
	}
	
	private void savePreferences() {
		//String path = Paths.get(System.getProperty("user.dir"), EProperty.FILE_PATH_LOGIN.getName()).toString();
		try (FileOutputStream fileOutputStream = new FileOutputStream(EProperty.FILE_PATH_LOGIN.getName())) {
			ps.store(fileOutputStream, null);
		} catch (IOException e) {
			logger.error("Impossible d'enregistrer les propriétés dans le fichier");
		}
	}
	
	public static void createFileLogin() {
		try {
			
			Path path = Paths.get(EProperty.FILE_PATH_LOGIN.getName());
			String credentials = "";
			if (!Files.exists(path)) {
				Files.write(path, credentials.getBytes());
			}
			
		} catch (IOException e) {
			logger.error("Impossible de créer le fichier");
		}
	}
	
	public static boolean fileLoginExist(){
		return Files.exists(Paths.get(EProperty.FILE_PATH_LOGIN.getName()));
	}
	
}
