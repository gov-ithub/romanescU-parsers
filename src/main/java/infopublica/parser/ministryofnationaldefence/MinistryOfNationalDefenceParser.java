package infopublica.parser.ministryofnationaldefence;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfNationalDefenceParser extends RssParser {

	public MinistryOfNationalDefenceParser() {
		super();
		setCategory("nationaldefence");
		setSourceOfData("http://www.mapn.ro/rss_feed/rss.php");
	}
}
