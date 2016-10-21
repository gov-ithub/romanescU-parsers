package infopublica.parser.ministryofeducation;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfEducationNewsParser extends RssParser {

	public MinistryOfEducationNewsParser() {
		super();
		setSourceOfData("http://www.edu.ro/rss.xml");
		setCategory("educationnews");
	}
}
