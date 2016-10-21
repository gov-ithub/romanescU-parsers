package infopublica.parser.ministryofinternalaffairs;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfInternalAffairsParser extends RssParser {

	public MinistryOfInternalAffairsParser() {
		super();
		setCategory("internalaffairs");
		setSourceOfData("http://www.comunicare.mai.gov.ro/Scripts/news/rss.php");
	}
}
