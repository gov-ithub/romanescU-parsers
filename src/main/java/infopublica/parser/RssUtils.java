package infopublica.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RssUtils {

	public static Date getPublicationDate(String className,
			String publicationDate) {
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		try {
			return formatter.parse(publicationDate);
		} catch (ParseException e) {
			System.out.println(className + " - Problem with the parsing of date: " + publicationDate + ". Trying with format yyyy-mm-dd.");
		}
		
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(publicationDate);
		} catch (ParseException e) {
			System.out.println(className + " - Problem with the parsing of date: " + publicationDate);
		}
		
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(publicationDate);
		} catch (ParseException e) {
			System.out.println(className + " - Problem with the parsing of date: " + publicationDate);
		}
		return date;
	}
}
