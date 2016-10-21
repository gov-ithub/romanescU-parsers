package infopublica.parser.ministryofhealth;

import org.springframework.stereotype.Component;

import infopublica.parser.RssParser;

@Component
public class MinistryOfHealthParser extends RssParser {

	public MinistryOfHealthParser() {
		super();
		setCategory("health");
		setSourceOfData("http://www.ms.ro/rss/comunicate.xml");
	}
	
}
