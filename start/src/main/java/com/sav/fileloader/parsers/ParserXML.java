package com.sav.fileloader.parsers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sav.fileloader.model.FileLoader;

/**
 * This class implements the method startParser the interface IParser.
 * 
 * @author AlexStrug
 *
 */
public class ParserXML implements IParser {

	private final static Logger LOGGER = LoggerFactory.getLogger(ParserXML.class);

	/**
	 * This method takes a file that has a format "JSON" and reads its contents.
	 * 
	 * f - path to the reference file.
	 */
	@Override
	public void startParser(String f) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;

		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOGGER.error("Parser Configuration exception: ");
			e.printStackTrace();
		}

		File file = new File(f);
		Document doc = null;

		try {
			try {
				doc = db.parse(file);
			} catch (IOException e) {
				LOGGER.error("IO exception: ");
				e.printStackTrace();
			}
		} catch (SAXException e) {
			LOGGER.error("SAX exception: ");
			e.printStackTrace();
		}

		Element docEle = doc.getDocumentElement();
		NodeList fileList = docEle.getElementsByTagName("file");

		if (fileList != null && fileList.getLength() > 0) {
			for (int i = 0; i < fileList.getLength(); i++) {

				Node node = fileList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					NodeList nodeList = e.getElementsByTagName("urlFile");
					FileLoader.fileURL.add(nodeList.item(0).getChildNodes().item(0).getNodeValue());
					nodeList = e.getElementsByTagName("nameFile");
					FileLoader.fileName.add(nodeList.item(0).getChildNodes().item(0).getNodeValue());
				}
			}
		}

	}

}
