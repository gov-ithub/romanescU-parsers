package infopublica.parser;

import infopublica.curator.StringCurator;
import infopublica.model.Channel;
import infopublica.model.Item;
import infopublica.model.RssFeed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class RssParser {

	private String sourceOfData;
	private String category;
	private RssFeed rssFeed;
	private int numberOfItems = 0;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public String getSourceOfData() {
		return sourceOfData;
	}

	public void setSourceOfData(String sourceOfData) {
		this.sourceOfData = sourceOfData;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public RssFeed getRssFeed() {
		return rssFeed;
	}

	public void setRssFeed(RssFeed rssFeed) {
		this.rssFeed = rssFeed;
	}

	public InputStream loadSourceOfData() throws IOException {
		URL rssSource;
		URLConnection connection = null;
		try {
			rssSource = new URL(sourceOfData);
			connection = rssSource.openConnection();
			connection.addRequestProperty("User-Agent", "Chrome/43.0.2357.124 m");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection.getInputStream();
	}
	
	public Reader loadReader() throws IOException {
		InputStream inputStream = loadSourceOfData();
		String inputStreamAsString = StringCurator.curateString(IOUtils.toString(inputStream, Charset.defaultCharset()));
		return new InputStreamReader(new ByteArrayInputStream(inputStreamAsString.getBytes()));
	}
	
	public boolean parseTheSourceOfData() {
		XMLEventReader reader;
		try {
			reader = getXMLEventReader();
			parseTheReader(reader);
		} catch (FactoryConfigurationError e) {
			logger.error("FactoryConfigurationError occured: " + e.getMessage());
			return false;
		} catch (XMLStreamException e) {
			logger.error("XMLStreamException occured: " + e.getMessage());
			return false;
		} catch (IOException e) {
			logger.error("Can't connect to: " + sourceOfData);
			logger.error("IOException occured: " + e.getMessage());
			return false;
		}
		return true;
	}

	private void parseTheReader(XMLEventReader reader)
			throws XMLStreamException {
		initializeModel();
		boolean currentInChannel = false;
		boolean currentInItem = false;
		Item newItem = new Item();
		while (reader.hasNext() && numberOfItems < 10) {
			XMLEvent xmlEvent = reader.nextEvent();
			if (xmlEvent.isStartElement()) {
				StartElement startElement = xmlEvent.asStartElement();
				String elementName = startElement.getName().getLocalPart()
						.trim();
				if (elementName.equals("channel")) {
					currentInChannel = true;
				} else if (elementName.equals("title")) {
					if (currentInChannel)
						rssFeed.getChannel().setTitle(reader.getElementText().trim());
					else if (currentInItem)
						newItem.setTitle(reader.getElementText().trim());
				} else if (elementName.equals("link")) {
					if (currentInChannel)
						rssFeed.getChannel().setLink(reader.getElementText().trim());
					else if (currentInItem)
						newItem.setLink(reader.getElementText().trim());
				} else if (elementName.equals("description")) {
					if (currentInChannel)
						rssFeed.getChannel().setDescription(StringCurator.curateString(reader.getElementText().trim()));
					else if (currentInItem)
						newItem.setDescription(StringCurator.curateString(reader.getElementText().trim()));
				} else if (elementName.equals("pubDate")) {
					if (currentInItem)
						newItem.setPubDate(RssUtils.getPublicationDate(this.getClass().getName(), reader.getElementText().trim()));
				} else if (elementName.equals("item")) {
					currentInItem = true;
					currentInChannel = false;
					newItem = new Item();
					newItem.setRetrievedDate(new Date());
				}
			} else if (xmlEvent.isEndElement()) {
				EndElement endElement = xmlEvent.asEndElement();
				String elementName = endElement.getName().getLocalPart().trim();
				if (elementName.equals("item")) {
					rssFeed.getChannel().getItems().add(newItem);
					numberOfItems += 1;
				}
			}
		}
	}

	private void initializeModel() {
		rssFeed = new RssFeed();
		rssFeed.setChannel(new Channel());
		rssFeed.getChannel().setItems(new ArrayList<Item>());
	}

	private XMLEventReader getXMLEventReader()
			throws FactoryConfigurationError, XMLStreamException, IOException {
		return XMLInputFactory.newInstance().createXMLEventReader(loadReader());
	}
}
