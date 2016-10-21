package infopublica.parser.ministryoftransportation;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfTransportationParser extends RssParser {
	
	public MinistryOfTransportationParser() {
		super();
		setCategory("transportation");
		setSourceOfData("http://www.mt.ro/web14/spatiul-media/comunicate-de-presa?format=feed&type=rss");
	}
}
