package infopublica.parser.ministryofexternalaffairs;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfExternalAffairsParser extends RssParser {

	public MinistryOfExternalAffairsParser() {
		super();
		setCategory("externalaffairs");
		setSourceOfData("http://www.mae.ro/rss.xml");
	}
}
