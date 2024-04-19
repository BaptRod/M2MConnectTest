package be.anb.rimex.m2mconnect.service.M2MConnect;

import java.util.Map;

public class M2MUpdateInfo {
	private boolean updateAvailable = false;
	
	private Map<String, String> filesChecksum;
	
	
	public boolean isUpdateAvailable() {
		return updateAvailable;
	}
	
	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}
	
	public Map<String, String> getFilesChecksum() {
		return filesChecksum;
	}
	
	public void setFilesChecksum(Map<String, String> filesChecksum) {
		this.filesChecksum = filesChecksum;
	}
}
