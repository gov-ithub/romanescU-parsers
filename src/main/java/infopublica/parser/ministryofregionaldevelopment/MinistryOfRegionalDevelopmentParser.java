package infopublica.parser.ministryofregionaldevelopment;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfRegionalDevelopmentParser extends RssParser {
	public MinistryOfRegionalDevelopmentParser() {
		super();
		setCategory("regionaldevelopment");
		setSourceOfData("http://www.mdrap.ro/rss.xml");
	}
}
