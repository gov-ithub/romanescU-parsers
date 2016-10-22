package infopublica.parser.ministryoffinance;

import org.springframework.stereotype.Component;

import infopublica.parser.RssParser;

@Component
public class MinistryOfFinanceParser extends RssParser {

	public MinistryOfFinanceParser() {
		super();
		setCategory("finance");
		setSourceOfData("http://www.mfinante.gov.ro/RssServlet?canal=comunicate");
	}
}
