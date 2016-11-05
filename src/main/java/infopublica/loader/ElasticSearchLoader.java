package infopublica.loader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import infopublica.model.Item;
import infopublica.model.RssFeed;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

@Component
public class ElasticSearchLoader {

	@Autowired
	private InfopublicaFeedsLoader infoPublicaFeedsLoader;
	
	@Resource
	private Environment environment;

	public TransportClient getElasticClient() throws UnknownHostException {
		TransportClient client = TransportClient.builder().settings(settings()).build();
		TransportAddress address = new InetSocketTransportAddress(
				InetAddress.getByName(environment.getProperty("elasticsearch.host")),
				Integer.parseInt(environment.getProperty("elasticsearch.port")));

		client.addTransportAddress(address);
		return client;
	}
	
	private Settings settings() {
		return Settings.builder()
			.put("cluster.name", environment.getProperty("elasticsearch.cluster"))
			.put("node.client", true)
			.put("client.transport.sniff", true)
			.put("client.transport.ignore_cluster_name", false)
			.build();
	}	

	public void loadIntoElastic() {
		// First we load everything from the feeds
		infoPublicaFeedsLoader.load();
		Map<String, RssFeed> feedsList = infoPublicaFeedsLoader.getFeedsList();
		TransportClient client = TransportClient.builder().build();
		try {
			client = getElasticClient();
		} catch (UnknownHostException ex) {
			System.out.println("Can't get Elastic Search connection!");
		}
		int sum = 0;
		int count = 0;
		for (Entry<String, RssFeed> entry : feedsList.entrySet()) {
			count = 0;
			List<Item> items = entry.getValue().getChannel().getItems();
			for (Item item : items) {
				try {
					elasticSearchInsert(item, entry.getKey(), client);
					count++;
				} catch (Exception ex) {
					System.out.println("Exception while inserting into Elastic Search");
				}
			}
			sum += count;
		}
		System.out.println("Number of news: " + sum);
		client.close();
	}

	public void elasticSearchInsert(final Item item, String category, TransportClient client) throws IOException, InterruptedException, ExecutionException {

		IndexRequest indexRequest = new IndexRequest("news", "new", "m-" + category + "-" + Integer.toString((item.getTitle() + item.getDescription()).hashCode()))
						.source(jsonBuilder().startObject()
							.field("title", item.getTitle())
							.field("description", item.getDescription())
							.field("date", getPubDate(item.getPubDate()))
							.field("link", item.getLink())
						.endObject());
		UpdateRequest updateRequest = new UpdateRequest("news", "new", "m-" + category + "-" + Integer.toString((item.getTitle() + item.getDescription()).hashCode()))
		        .doc(jsonBuilder()
		            .startObject()
		            	.field("title", item.getTitle())
		            	.field("description", item.getDescription())
		            	.field("date", getPubDate(item.getPubDate()))
		            	.field("link", item.getLink())
		            .endObject())
		        .upsert(indexRequest);              
		client.update(updateRequest).get();
	}
	
	private long getPubDate(Date date) {
		if (date != null)
			return date.getTime();
		else
			return new Date().getTime();
	}
}
