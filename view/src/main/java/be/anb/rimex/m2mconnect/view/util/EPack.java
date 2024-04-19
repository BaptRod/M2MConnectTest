package be.anb.rimex.m2mconnect.view.util;

public enum EPack {
	BASE_PACK("5.17266.10_SP_"),
	SP_01("5.17266.10_SP_01", "3MB & 300SMS"),
	SP_02("5.17266.10_SP_02", "3MB & 300SMS & appel en absence"),
	SP_03("5.17266.10_SP_03", "1MB 100SMS BE ONLY"),
	SP_04("5.17266.10_SP_04", "1MB EUROPE"),
	SP_05("5.17266.10_SP_05", "1MB 100SMS 15MIN VOICE"),
	SP_06("5.17266.10_SP_06", "1MB 100SMS 30MIN VOICE"),
	SP_07("5.17266.10_SP_07", "15MIN VOICE OUT"),
	SP_08("5.17266.10_SP_08", "30MIN VOICE OUT"),
	SP_09("5.17266.10_SP_09", "50 SMS"),
	SP_10("5.17266.10_SP_10", "25MB & 50SMS BE ONLY"),
	SP_11("5.17266.10_SP_11", "1MB EUROPE PUBLIC IP"),
	SP_12("5.17266.10_SP_12", "1GB PUBLIC IP"),
	SP_13("5.17266.10_SP_13", "2GB PUBLIC IP"),
	SP_14("5.17266.10_SP_14", "5GB PUBLIC IP"),
	SP_15("5.17266.10_SP_15", "30 GB"),
	SP_16("5.17266.10_SP_16", "50 GB"),
	SP_17("5.17266.10_SP_17", "15MIN VOICE 30SMS");
	
	private final String code;
	private final String description;
	
	EPack(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	EPack(String code) {
		this.code = code;
		this.description = "";
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static String getDescriptionByCode(String code) {
		for (EPack plan : EPack.values()) {
			if (plan.getCode().equals(code)) {
				return plan.getDescription();
			}
		}
		return null;
	}
}
