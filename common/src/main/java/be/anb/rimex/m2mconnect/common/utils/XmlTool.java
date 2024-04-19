package be.anb.rimex.m2mconnect.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlTool {
	private static final Logger LOGGER = LogManager.getLogger(XmlTool.class);
	
	private XmlTool() {
	}
	
	/**
	 * Convert an objct to XML String.
	 *
	 * @param objToConvert the object to convert.
	 * @return the xml string.
	 */
	public static String convertObjectToXml(final Object objToConvert) {
		if (objToConvert == null) {
			return null;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(objToConvert.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(objToConvert, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			LOGGER.error("Error during the convetion : Object to XML...", e);
		}
		return null;
	}
	
	/**
	 * Convert Object to XML file.
	 *
	 * @param xmlFile the xml file
	 * @param objToConvert the object to convert.
	 * @return the xml string.
	 */
	public static boolean convertObjectToXml(final File xmlFile, final Object objToConvert) {
		if (objToConvert == null || xmlFile == null || !xmlFile.exists() || xmlFile.isDirectory()) {
			return false;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(objToConvert.getClass());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(objToConvert, xmlFile);
			return true;
		} catch (JAXBException e) {
			LOGGER.error("Error during the convetion : Object to XML...", e);
		}
		return false;
	}
	
	/**
	 * Convert an xml string to an object.
	 *
	 * @param xml the xml to convert.
	 * @param targetClass the target class
	 * @return the object converted.
	 */
	public static <T> T convertXmlToObject(final String xml, final Class<T> targetClass) {
		if (xml == null || xml.isEmpty()) {
			return null;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(targetClass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			LOGGER.error("Error during the convetion : XML to Object... TargetClass =" + targetClass.getName(), e);
		}
		return null;
	}
	
	/**
	 * Convert an xml to an object.
	 *
	 * @param xmlStreamReader the xml stream reader.
	 * @param targetClass the target class
	 * @return the object converted.
	 */
	public static <T> T convertXmlToObject(final XMLStreamReader xmlStreamReader, final Class<T> targetClass) {
		if (xmlStreamReader == null) {
			return null;
		}
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(targetClass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (T) unmarshaller.unmarshal(xmlStreamReader);
		} catch (JAXBException e) {
			LOGGER.error("Error during the convetion : XML to Object... TargetClass =" + targetClass.getName(), e);
		}
		return null;
	}
	
	public static XMLStreamReader createXmlStreamReader(String filePath) {
		try {
			return XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(filePath));
		} catch (XMLStreamException | FileNotFoundException ex) {
			LOGGER.error("XMLStreamException or FileNotFoundException...", ex);
			return null;
		}
	}
	
	
	public static XMLStreamReader createXmlStreamReader(File file) {
		try {
			return XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(file));
		} catch (XMLStreamException | FileNotFoundException ex) {
			LOGGER.error("XMLStreamException or FileNotFoundException...", ex);
			return null;
		}
	}

}
