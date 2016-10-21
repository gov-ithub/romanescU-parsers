package infopublica.parser.ministryofeducation;

import infopublica.parser.RssParser;

import org.springframework.stereotype.Component;

@Component
public class MinistryOfEducationPressReleaseParser extends RssParser {
	
	public MinistryOfEducationPressReleaseParser() {
		super();
		setSourceOfData("http://www.edu.ro/index.php/pressrel?theme=rss");
		setCategory("education-press");
	}
}
