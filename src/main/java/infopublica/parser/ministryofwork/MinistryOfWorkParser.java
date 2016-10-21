package infopublica.parser.ministryofwork;

import org.springframework.stereotype.Component;

import infopublica.parser.RssParser;

@Component
public class MinistryOfWorkParser extends RssParser {
	public MinistryOfWorkParser() {
		super();
		setCategory("work");
		setSourceOfData("http://www.mmuncii.ro/j33/index.php/ro/comunicare/comunicate-de-presa?format=feed&type=rss");
	}

}
