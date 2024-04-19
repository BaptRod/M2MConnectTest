module be.anb.rimex.m2mconnect.service {
	requires org.apache.logging.log4j;
	requires be.anb.rimex.m2mconnect.common;
	requires java.net.http;
	requires com.fasterxml.jackson.databind;
	requires jsch;
	requires org.apache.commons.codec;
	requires maven.artifact;
	opens be.anb.rimex.m2mconnect.service.request to com.fasterxml.jackson.databind;
	opens be.anb.rimex.m2mconnect.service.response to com.fasterxml.jackson.databind;
	exports be.anb.rimex.m2mconnect.service.response;
	exports be.anb.rimex.m2mconnect.service;
	exports be.anb.rimex.m2mconnect.service.exception;
	exports be.anb.rimex.m2mconnect.service.M2MConnect;
	
}