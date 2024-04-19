package be.anb.rimex.m2mconnect.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Encrypt {
	private static final Logger LOGGER = LogManager.getLogger(Encrypt.class);
	
	private static String key = "Tri54321Tri12345"; //
	private static String initVector = "1593576548523549";
	
	public static void BytesToChar(byte[] a, char[] b, int lng) {
		for (int i = 0; i < lng; i++) {
			b[i] = (char) (a[i] & 0xFF);
		}
	}
	
	public static int hex2decimal(String s) {
		String digits = "0123456789ABCDEF";
		s = s.toUpperCase();
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = digits.indexOf(c);
			val = 16 * val + d;
		}
		return val;
	}
	
	public static char[] PasswordToBCD(String S) {
		char[] c = new char[]{(char) 15, (char) 15, (char) 15, (char) 15, (char) 15, (char) 15, (char) 15, (char) 15};
		int x = 0;
		while ((x < 8) && (x < S.length())) {
			c[x] = (char) (((byte) S.charAt(x)) - 0x30);
			x++;
		}
		return c;
	}
	
	
	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			
			byte[] encrypted = cipher.doFinal(value.getBytes());
			
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			LOGGER.error("Encrypt Error...", ex);
		}
		
		return null;
	}
	
	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			
			return new String(original);
		} catch (Exception ex) {
			LOGGER.error("Decrypt Error...", ex);
		}
		
		return null;
	}
	
	public static String encryptSha256(String data) {
		if (data == null || data.isEmpty()) {
			return "";
		}
		return DigestUtils.sha256Hex(data);
	}
}
