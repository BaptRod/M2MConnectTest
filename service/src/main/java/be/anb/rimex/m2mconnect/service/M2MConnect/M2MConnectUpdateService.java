package be.anb.rimex.m2mconnect.service.M2MConnect;
import be.anb.rimex.m2mconnect.common.AppProperties;
import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.object.M2MConnect;
import be.anb.rimex.m2mconnect.common.utils.Encrypt;
import be.anb.rimex.m2mconnect.common.utils.XmlTool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.codec.digest.DigestUtils;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MException;
import be.anb.rimex.m2mconnect.service.ftp.SFTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ComparableVersion;
public class M2MConnectUpdateService {
	
	private static Logger LOGGER = LogManager.getLogger(M2MConnectUpdateService.class);
	
	private String rootDir;
	private String targetLocalDir;
	private String targetFile;
	
	private EOsType eOsType;
	private String checkSeparator;
	
	public M2MConnectUpdateService(){
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			targetLocalDir = "./update";
			targetFile = "M2MConnect_Setup.exe";
			rootDir = "/home/updatetrcm/updateM2MConnect/";
			eOsType = EOsType.WINDOWS;
			checkSeparator = " \\*";
		} else {
			targetLocalDir = "update";
			targetFile = "M2MConnect_Setup.pkg";
			rootDir = "/home/updatetrcm/updateM2MConnect/";
			eOsType = EOsType.MACOS;
			checkSeparator = "  ";
		}
	
	}
	
	
	
	private SFTPClient initFTPClientService() throws M2MException {
		SFTPClient ftpClientService = new SFTPClient(AppProperties.getInstance().getValueOfProperty(EProperty.FTP_URL), AppProperties.getInstance().getValueOfProperty(EProperty.FTP_USERNAME), Encrypt.decrypt(AppProperties.getInstance().getValueOfProperty(EProperty.FTP_PASSWORD)), rootDir);
		
		boolean connected = false;
		int cpt = 1;
		while (!connected && cpt > 0) {
			try {
				ftpClientService.getSFTPSession();
				connected = true;
			} catch (Exception e) {
				LOGGER.error("Error connect FTP...", e);
			}
			cpt--;
		}
		if (!connected) {
			LOGGER.error("Not connected to FTP...");
			throw new M2MException("Not connected to FTP...", EM2MError.ERROR_FTP_CONNEXION);
		}
		
		return ftpClientService;
	}
	
	public M2MUpdateInfo isUpdateAvailable() throws Exception {
		M2MUpdateInfo m2MUpdateInfo = new M2MUpdateInfo();
		
		ComparableVersion currentVerion = new ComparableVersion(AppProperties.getInstance().getValueOfProperty(EProperty.APP_VERSION));
		SFTPClient ftpClientService = initFTPClientService();
		try {
			m2MUpdateInfo.setFilesChecksum(getFileCheckSum(ftpClientService));
		} catch (Exception e) {
			throw new M2MException("Error download FTP...", EM2MError.ERROR_FTP_DOWNLOAD);
		}
		
		if (downloadAndCheckFile(ftpClientService, m2MUpdateInfo, "M2MConnect.xml", targetLocalDir, "M2MConnect.xml")) {
			M2MConnect m2MConnect= XmlTool.convertXmlToObject(XmlTool.createXmlStreamReader(new File(targetLocalDir + "/M2MConnect.xml")), M2MConnect.class);
			if (m2MConnect != null && m2MConnect.getVersion() != null) {
				ComparableVersion newVersion = new ComparableVersion(m2MConnect.getVersion().replace("-SNAPSHOT", ""));
				m2MUpdateInfo.setUpdateAvailable(newVersion.compareTo(currentVerion) > 0);
			}
		} else {
			throw new M2MException("Error download FTP...", EM2MError.ERROR_FTP_DOWNLOAD);
		}
		
		return m2MUpdateInfo;
	}
	
	
	public void downloadNewM2MConnect(M2MUpdateInfo m2mUpdateInfo) throws Exception {
		if (!downloadAndCheckFile(initFTPClientService(), m2mUpdateInfo, targetFile, targetLocalDir,
			targetFile)) {
			throw new M2MException("Error download FTP...", EM2MError.ERROR_FTP_DOWNLOAD);
		}
	}
	
	public void processUpdate(M2MUpdateInfo m2mUpdateInfo) throws Exception {
		switch (eOsType) {
			case WINDOWS:
				new ProcessBuilder("./update/" + targetFile).start();
				break;
			case MACOS:
				new ProcessBuilder("open", "update/" + targetFile).start();
				break;
		}
		System.exit(0);
	}
	
	private Map<String, String> getFileCheckSum(SFTPClient ftp) throws Exception {
		Map<String, String> md5SumByFileName = new HashMap<>();
		
		File allCheckSumFile = downloadFile(ftp, "files.chk", targetLocalDir, "files.chk");
		
		try (Stream<String> stream = Files.lines(Paths.get(allCheckSumFile.toURI()))) {
			stream.forEach(l -> {
				String[] part = l.split(checkSeparator);
				if (part.length == 2) {
					md5SumByFileName.put(part[1], part[0]);
				}
			});
		} catch (IOException e) {
			LOGGER.error("Error... ", e);
			throw e;
		}
		
		return md5SumByFileName;
	}
	
	private static boolean downloadAndCheckFile(SFTPClient ftpClientService, M2MUpdateInfo m2mUpdateInfo,
		String targetFTPFileName, String targetDirectory, String targetFileName) throws Exception {
		File file = downloadFile(ftpClientService, targetFTPFileName, targetDirectory, targetFileName);
		if (file == null) {
			return false;
		}
		
		String currentSum;
		try (InputStream is = new FileInputStream(file)) {
			currentSum = DigestUtils.md5Hex(is).toUpperCase();
		}
		
		if (currentSum != null && !currentSum.isEmpty()) {
			String md5SumExpected = m2mUpdateInfo.getFilesChecksum().get(targetFTPFileName).toUpperCase();
			if (currentSum.toUpperCase().equals(md5SumExpected)) {
				LOGGER.info(targetFTPFileName + " : Valid CheckSum...");
				return true;
			}
			LOGGER.warn(targetFTPFileName + " : invalid CheckSum!!!");
		}
		return false;
	}
	
	private static File downloadFile(SFTPClient fTPUploader, String targetFTPFileName, String targetDirectory, String targetFileName) throws Exception {
		LOGGER.info(targetFTPFileName + " : file downloading START...");
		File file = null;
		try {
			file = fTPUploader.downloadFile(targetFTPFileName, targetDirectory + "/" + targetFileName);
		} catch (Exception e) {
			LOGGER.warn(targetFTPFileName + " file download error...");
			throw e;
		}
		if (file == null || !file.exists()) {
			return null;
		}
		LOGGER.info(targetFTPFileName + " : file download DONE...");
		return file;
	}
	
}
