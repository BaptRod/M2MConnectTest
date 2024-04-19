package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.common.AppProperties;
import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.service.M2MConnect.M2MConnectUpdateService;
import be.anb.rimex.m2mconnect.service.M2MConnect.M2MUpdateInfo;
import be.anb.rimex.m2mconnect.service.exception.M2MException;
import be.anb.rimex.m2mconnect.view.Application;
import java.io.File;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class M2MUpdateService {
	
	private static Logger LOGGER = LogManager.getLogger(M2MUpdateService.class);
	
	public static void checkUpdates() {
		Executors.newSingleThreadExecutor().submit(() -> {
			try {
				File updateDir = new File("./update");
				if (!updateDir.exists()) {
					updateDir.mkdir();
				}
				
				M2MConnectUpdateService m2MConnectUpdateService = new M2MConnectUpdateService();
				M2MUpdateInfo m2mUpdateInfo = m2MConnectUpdateService.isUpdateAvailable();
				if (m2mUpdateInfo != null && m2mUpdateInfo.isUpdateAvailable()) {
					try {
						m2MConnectUpdateService.downloadNewM2MConnect(m2mUpdateInfo);
						
						Platform.runLater(() -> {
							try {
								boolean doIt = Application.showAlert(AppProperties.getInstance().getValueOfProperty(EProperty.UPDATE_MESSAGE) , AlertType.CONFIRMATION);
								if (doIt) {
									m2MConnectUpdateService.processUpdate(m2mUpdateInfo);
								}
							} catch (Exception e) {
								if (e instanceof M2MException) {
									Application.showErrorAlert(((M2MException) e).getCode().name());
								}
								else{
									Application.showErrorAlert(e.getMessage());
								}
							}
						});
					} catch (Exception e) {
						LOGGER.warn("Error update...", e);
					}
				}
				
				if (m2mUpdateInfo != null && !m2mUpdateInfo.isUpdateAvailable()) {
					LOGGER.info("M2M is up to date : " + AppProperties.getInstance().getValueOfProperty(EProperty.APP_VERSION));
				}
			} catch (Exception e) {
				LOGGER.warn("Error update...", e);
			}
		});
	}

}
