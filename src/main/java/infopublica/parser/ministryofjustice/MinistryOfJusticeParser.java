package infopublica.parser.ministryofjustice;

import org.springframework.stereotype.Component;

import infopublica.parser.RssParser;

@Component
public class MinistryOfJusticeParser extends RssParser {
	
	public MinistryOfJusticeParser() {
		super();
		setCategory("justice");
		setSourceOfData("http://www.just.ro/feed/");
	}

}
