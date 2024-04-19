module be.anb.rimex.m2mconnect.common {
	requires org.apache.logging.log4j;
	requires org.apache.poi.poi;
	requires org.apache.poi.ooxml;
	requires org.apache.commons.codec;
	requires java.xml;
	requires jakarta.xml.bind;
	exports be.anb.rimex.m2mconnect.common;
	exports be.anb.rimex.m2mconnect.common.utils;
	exports be.anb.rimex.m2mconnect.common.object;
	opens be.anb.rimex.m2mconnect.common.object to jakarta.xml.bind;
	
}