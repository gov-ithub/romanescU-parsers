package infopublica.parser.ministryofeducation;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfEducationLegalDocsParser extends RssParser {
	
	public MinistryOfEducationLegalDocsParser() {
		super();
		setSourceOfData("http://www.edu.ro/index.php/legaldocs?theme=rss");
		setCategory("education-legal");
	}
}
