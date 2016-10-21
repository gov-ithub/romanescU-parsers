package infopublica.parser.government;

import org.springframework.stereotype.Component;

import infopublica.parser.RssParser;

@Component
public class GovernmentParser extends RssParser {

	public GovernmentParser() {
		super();
		setCategory("government");
		setSourceOfData("http://gov.ro/ro/rss");
	}
	
}
