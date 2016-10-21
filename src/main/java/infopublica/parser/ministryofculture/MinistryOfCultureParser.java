package infopublica.parser.ministryofculture;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfCultureParser extends RssParser {

	public MinistryOfCultureParser() {
		super();
		setCategory("culture");
		setSourceOfData("http://www.cultura.ro/feed");
	}
}
