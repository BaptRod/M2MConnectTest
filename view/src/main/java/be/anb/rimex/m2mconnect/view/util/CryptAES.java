package be.anb.rimex.m2mconnect.view.util;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptAES {
	private static String secretKey = "vZqVURMusAwMa87B49xjqxrxCt0KTN0m+MHP4Md7dQs=";
	
	private static final Logger logger = LogManager.getLogger(CryptAES.class);
	
	public static SecretKey generateAESKey() {
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}
	
	
	public static String encrypt(String strToEncrypt)  {
		
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, generateAESKey());
			byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(encryptedBytes);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return "";
		}
	}
	
	
	public static String decrypt(String decrypt)  {
		try {
			byte[] bytesToDecrypt = Base64.getDecoder().decode(decrypt);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, generateAESKey());
			byte[] decryptedBytes = cipher.doFinal(bytesToDecrypt);
			return new String(decryptedBytes, StandardCharsets.UTF_8);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return "";
		}
	}

}
