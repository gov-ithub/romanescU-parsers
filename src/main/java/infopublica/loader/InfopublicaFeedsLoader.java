package infopublica.loader;

import infopublica.model.Item;
import infopublica.model.RssFeed;
import infopublica.parser.RssParser;
import infopublica.parser.government.GovernmentParser;
import infopublica.parser.ministryofculture.MinistryOfCultureParser;
import infopublica.parser.ministryofeducation.MinistryOfEducationNewsParser;
import infopublica.parser.ministryofeuropeanfunds.MinistryOfEuropeanFundsParser;
import infopublica.parser.ministryofexternalaffairs.MinistryOfExternalAffairsParser;
import infopublica.parser.ministryofexternalaffairs.MinistryOfExternalAffairsTravelWarningsParser;
import infopublica.parser.ministryoffinance.MinistryOfFinanceParser;
import infopublica.parser.ministryofhealth.MinistryOfHealthParser;
import infopublica.parser.ministryofinternalaffairs.MinistryOfInternalAffairsParser;
import infopublica.parser.ministryofjustice.MinistryOfJusticeParser;
import infopublica.parser.ministryofnationaldefence.MinistryOfNationalDefenceParser;
import infopublica.parser.ministryofregionaldevelopment.MinistryOfRegionalDevelopmentParser;
import infopublica.parser.ministryoftransportation.MinistryOfTransportationParser;
import infopublica.parser.ministryofwork.MinistryOfWorkParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfopublicaFeedsLoader {

	@Autowired
	private MinistryOfEducationNewsParser moeNewsParser;
	@Autowired
	private MinistryOfInternalAffairsParser miaParser;
	@Autowired
	private MinistryOfExternalAffairsTravelWarningsParser meaTravelWarningsParser;
	@Autowired
	private MinistryOfNationalDefenceParser mndParser;
	@Autowired
	private MinistryOfCultureParser cultureParser;
	@Autowired
	private MinistryOfRegionalDevelopmentParser mrdParser;
	@Autowired
	private MinistryOfEuropeanFundsParser mefParser;
	@Autowired
	private MinistryOfTransportationParser mtParser;
	@Autowired
	private MinistryOfExternalAffairsParser meaParser;
	@Autowired
	private MinistryOfJusticeParser justiceParser;
	@Autowired
	private MinistryOfHealthParser healthParser;
	@Autowired
	private MinistryOfFinanceParser financeParser;
	@Autowired
	private MinistryOfWorkParser workParser;
	@Autowired
	private GovernmentParser govParser;
	

	private List<RssParser> parsersList = new ArrayList<RssParser>();
	
	final static Logger logger = Logger.getLogger(InfopublicaFeedsLoader.class);

	private void loadRssFeedsList() {
		parsersList.add(mndParser);
		parsersList.add(mrdParser);
		parsersList.add(miaParser);
		parsersList.add(mefParser);
		parsersList.add(mtParser);
		parsersList.add(moeNewsParser);
		parsersList.add(meaTravelWarningsParser);
		parsersList.add(meaParser);
		parsersList.add(cultureParser);
		parsersList.add(justiceParser);
		parsersList.add(healthParser);
		parsersList.add(financeParser);
		parsersList.add(workParser);
		parsersList.add(govParser);
	}

	private Map<String, RssFeed> feedsList = new HashMap<String, RssFeed>();

	public void load() {
		loadRssFeedsList();
		for (RssParser parser : parsersList) {
			boolean parseOk = parser.parseTheSourceOfData();
			if (parseOk) {
				addToFeedsList(parser.getCategory(), parser.getRssFeed());
				logger.info("Added this category: " + parser.getCategory() + " for this feed: " + parser.getRssFeed());
			}
		}
	}

	private void addToFeedsList(String category, RssFeed rssFeed) {
		if (rssFeed == null) {
			logger.error("Couldn't add null RSS feed into list!");
			return;
		}
		if (feedsList.containsKey(category))
			feedsList.get(category).getChannel().getItems().addAll(rssFeed.getChannel().getItems());
		else
			feedsList.put(category, rssFeed);
	}

	public Map<String, RssFeed> getFeedsList() {
		return feedsList;
	}
	
	public List<Item> getItemsForCatory(String category) {
		logger.info("Trying to get this category: " + category);
		if(feedsList.isEmpty())
			load();
		return feedsList.get(category).getChannel().getItems();
	}
}
