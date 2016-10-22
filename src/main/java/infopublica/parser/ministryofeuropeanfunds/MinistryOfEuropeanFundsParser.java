package infopublica.parser.ministryofeuropeanfunds;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfEuropeanFundsParser extends RssParser {
	public MinistryOfEuropeanFundsParser() {
		super();
		setCategory("european-funds");
		setSourceOfData("http://old.fonduri-ue.ro/index.php?option=com_ninjarsssyndicator&feed_id=3&format=raw");
	}

}
