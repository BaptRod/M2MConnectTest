package be.anb.rimex.m2mconnect.view.util;

import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;

public class Translate {
	
	public static String getTranslateForPack(String pack){
		String result = "";
		switch (pack) {
			case "3MB & 300SMS & appel en absence":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MB3_300SMS_CALL.getName());
				break;
			case "1MB 100SMS BE ONLY":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MB1_100SMS_BE_ONLY.getName());
				break;
			case "1MB 100SMS 30MIN VOICE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MB1_100SMS_30MIN_VOICE.getName());
				break;
			case "30MIN VOICE OUT":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MIN30_VOICE_OUT.getName());
				break;
			case "25MB & 50SMS BE ONLY":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MB25_50SMS_BE_ONLY.getName());
				break;
			case "15MIN VOICE OUT":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MIN15_VOICE_OUT.getName());
				break;
			case "1MB 100SMS 15MIN VOICE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MB1_100SMS_15MIN_VOICE.getName());
				break;
			case "15MIN VOICE 30SMS":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.MIN15_VOICE_30SMS.getName());
				break;
			default:
				result = pack;
			
		}
		return result;
	}
	
	public static String getTranslateState(String state){
		String result = "";
		switch (state) {
			case "ACTIVE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.ACTIVE.getName());
				break;
			case "DEACTIVATED":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.DESACTIVATE.getName());
				break;
			case "PAUSE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.BREAK.getName());
				break;
			default:
				result = state;
		}
		return  result;
	}
	
}
