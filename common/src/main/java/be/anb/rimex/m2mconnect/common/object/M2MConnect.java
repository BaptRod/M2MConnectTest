package be.anb.rimex.m2mconnect.common.object;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "M2MConnect")
public class M2MConnect implements Serializable {
	private String version;
	
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
}
