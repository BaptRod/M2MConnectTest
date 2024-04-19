package be.anb.rimex.m2mconnect.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.*;
import java.nio.file.*;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppProperties {
	private static final Logger logger = LogManager.getLogger(AppProperties.class);
	private static AppProperties instance;
	private Properties ps;
	
	
	
	private AppProperties() {
	}
	
	public static AppProperties getInstance() {
		if (instance == null) {
			synchronized (AppProperties.class) {
				if (instance == null) {
					instance = new AppProperties();
						instance.loadProperties();
					
					
				}
			}
		}
		return instance;
	}
	
	private void loadProperties() {
		Properties properties = new Properties();
		try(InputStream resourceAsStream = AppProperties.class.getResourceAsStream(EProperty.FILE_PATH_APPLICATION.getName())) {
			properties.load(resourceAsStream);
			ps = properties;
			
		} catch (IOException e) {
			logger.error("Impossible de trouver le fichier de propriétés");
		}
	}
	
	public String getValueOfProperty(EProperty eProperty) {
		return ps.getProperty(eProperty.getName());
	}
	
	
	
	
	
	
	
	
}
