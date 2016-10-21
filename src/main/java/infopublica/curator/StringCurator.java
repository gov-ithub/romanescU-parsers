package infopublica.curator;

import java.util.ArrayList;
import java.util.List;

public class StringCurator {
	
	final static List<String> curationList = new ArrayList<String>();
	
	static {
		curationList.add("\\&middot\\;");
		curationList.add("\\&mdash\\;");
		curationList.add("\\&uacute\\;");
		curationList.add("`");
	}
	
	public static String curateString(String textToCurate) {
		for(String curator : curationList) {
			textToCurate = textToCurate.replaceAll(curator, "");
		}
		textToCurate = textToCurate.trim().replaceFirst("^([\\W]+)<","<");
		return textToCurate;
	}
	
	

}
