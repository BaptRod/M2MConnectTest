package be.anb.rimex.m2mconnect.service.ftp;

import com.jcraft.jsch.Logger;
import org.apache.logging.log4j.LogManager;

public class JschLogger implements Logger {
	
	private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(JschLogger.class);
	
	@Override
	public boolean isEnabled(int i) {
		return true;
	}
	
	@Override
	public void log(int i, String string) {
		LOGGER.info(string);
	}

}
