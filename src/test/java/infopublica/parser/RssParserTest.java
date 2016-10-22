package infopublica.parser;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import infopublica.configuration.SpringJavaConfiguration;
import infopublica.model.Item;
import infopublica.parser.government.GovernmentParser;
import infopublica.parser.ministryofculture.MinistryOfCultureParser;
import infopublica.parser.ministryofeducation.MinistryOfEducationLegalDocsParser;
import infopublica.parser.ministryofeducation.MinistryOfEducationNewsParser;
import infopublica.parser.ministryofexternalaffairs.MinistryOfExternalAffairsParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringJavaConfiguration.class})
public class RssParserTest {
	
	@Autowired
	private MinistryOfEducationNewsParser moeNewsParser;
	@Autowired
	private MinistryOfEducationLegalDocsParser moeLegalDocsParser;
	@Autowired
	private MinistryOfCultureParser cultureParser;
	
	@Autowired
	private MinistryOfExternalAffairsParser maeParser;
	
	@Autowired
	private GovernmentParser govParser;
	
	@Test
	public void testLoadSourceOfData() throws IOException {
		moeNewsParser.setSourceOfData("http://www.edu.ro/index.php/base/noutati?theme=rss");
		InputStream content = moeNewsParser.loadSourceOfData();
		assertNotNull(content);
	}

	@Test
	public void testParsingOfSource() throws XMLStreamException, IOException {
		moeNewsParser.setSourceOfData("http://www.edu.ro/index.php/base/noutati?theme=rss");
		moeNewsParser.parseTheSourceOfData();
		assertNotNull(moeNewsParser.getRssFeed().getChannel());
	}
	
	@Test
	public void testPublicationDate() throws XMLStreamException, IOException {
		moeLegalDocsParser.parseTheSourceOfData();
		assertNotNull(moeLegalDocsParser.getRssFeed().getChannel().getItems().get(0).getPubDate());
	}
	
	@Test
	public void testMinistryOfCulturePublicationDate() throws XMLStreamException, IOException {
		cultureParser.parseTheSourceOfData();
		for(Item item : cultureParser.getRssFeed().getChannel().getItems()) {
			System.out.println(item);
			System.out.println(item.getPubDate());
		}
		assertNotEquals(cultureParser.getRssFeed().getChannel().getItems().get(0).getPubDate(), new Date());
	}
	
	@Test
	public void testMAEParser() throws XMLStreamException, IOException {
		maeParser.parseTheSourceOfData();
		for(Item item : maeParser.getRssFeed().getChannel().getItems()) {
			System.out.println(item);
			System.out.println(item.getPubDate());
		}
		assertNotEquals(maeParser.getRssFeed().getChannel().getItems().get(0).getPubDate(), new Date());
	}
	
	@Test
	public void testGovernmentParser() throws XMLStreamException, IOException {
		govParser.parseTheSourceOfData();
		for(Item item : govParser.getRssFeed().getChannel().getItems()) {
			System.out.println(item);
			System.out.println(item.getPubDate());
		}
		assertNotEquals(govParser.getRssFeed().getChannel().getItems().get(0).getPubDate(), new Date());
	}
}
