package infopublica.parser.ministryofexternalaffairs;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfExternalAffairsTravelWarningsParser extends RssParser {

	public MinistryOfExternalAffairsTravelWarningsParser() {
		super();
		setCategory("externalaffairstravelwarnings");
		setSourceOfData("http://www.mae.ro/warnings/feed");
	}
}
