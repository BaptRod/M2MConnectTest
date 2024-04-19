package be.anb.rimex.m2mconnect.service.ftp;

import com.jcraft.jsch.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SFTPClient {
	
	private static Logger LOGGER = LogManager.getLogger(SFTPClient.class);
	
	private JschSession jschSession = null;
	
	private String host;
	private String user;
	private String pwd;
	private String rootPath;
	
	public SFTPClient(String host, String user, String pwd, String rootPath) {
		this.host = host;
		this.user = user;
		this.pwd = pwd;
		this.rootPath = rootPath;
	}
	
	
	public JschSession getSFTPSession() throws Exception {
		return getSFTPSession(150000);
	}
	
	public JschSession getSFTPSession(int timeOut) throws Exception {
		LOGGER.info("SFTP-001");
		if (jschSession == null || jschSession.getChannelSftp() == null || jschSession.getSession() == null
			|| jschSession.getChannelSftp().isClosed() || !jschSession.getSession().isConnected()) {
			Session session = null;
			Channel channel = null;
			ChannelSftp sftp = null;
			try {
				LOGGER.info("SFTP-002");
				JSch.setLogger(new JschLogger());
				final JSch ssh = new JSch();
				LOGGER.info("SFTP-003");
				session = ssh.getSession(user, host, 22);
				LOGGER.info("SFTP-004");
				session.setPassword(pwd);
				session.setTimeout(timeOut);
				session.setConfig("StrictHostKeyChecking", "no");
				LOGGER.info("SFTP-005");
				session.connect();
				LOGGER.info("SFTP-006");
				channel = session.openChannel("sftp");
				LOGGER.info("SFTP-007");
				channel.connect();
				LOGGER.info("SFTP-008");
				
				sftp = (ChannelSftp) channel;
				sftp.cd(rootPath);
				
				LOGGER.info("Connexion SFTP sur " + host + " a ete etablir. ");
			} catch (final JSchException e) {
				LOGGER.error("Error...", e);
				if (channel != null) {
					channel.disconnect();
				}
				if (session != null) {
					session.disconnect();
				}
				throw e;
			}
			jschSession = new JschSession(session, sftp);
		}
		LOGGER.info("SFTP-009");
		return jschSession;
		
	}
	
	public File downloadFile(String remotefilename, String localfilename) throws Exception {
		return downloadFile(remotefilename, localfilename, true);
	}
	
	public File downloadFile(String remotefilename, String localfilename, boolean deletePreviousFile)
		throws Exception {
		File file = new File(localfilename);
		if (deletePreviousFile && file.exists()) {
			file.delete();
		}
		JschSession currentJschSession = null;
		OutputStream outputStream = null;
		try {
			currentJschSession = getSFTPSession();
			if (currentJschSession != null) {
				outputStream = new BufferedOutputStream(new FileOutputStream(file));
				
				currentJschSession.getChannelSftp().get(remotefilename, outputStream);
			}
			
			return file;
		} catch (final Exception e) {
			LOGGER.error("Error...", e);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
		
		return null;
	}
	
	public void disconnect() {
		if (jschSession != null && jschSession.getChannelSftp() != null) {
			jschSession.getChannelSftp().disconnect();
		}
		if (jschSession != null && jschSession.getSession() != null) {
			jschSession.getSession().disconnect();
		}
	}
	
	private class JschSession {
		
		private Session session;
		private ChannelSftp channelSftp;
		
		public JschSession(Session session, ChannelSftp channelSftp) {
			this.session = session;
			this.channelSftp = channelSftp;
		}
		
		public Session getSession() {
			return session;
		}
		
		public void setSession(Session session) {
			this.session = session;
		}
		
		public ChannelSftp getChannelSftp() {
			return channelSftp;
		}
		
		public void setChannelSftp(ChannelSftp channelSftp) {
			this.channelSftp = channelSftp;
		}
		
	}
	
	

}
