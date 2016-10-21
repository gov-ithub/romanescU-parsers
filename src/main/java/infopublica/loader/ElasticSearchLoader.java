package infopublica.loader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import infopublica.model.Item;
import infopublica.model.RssFeed;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

@Component
public class ElasticSearchLoader {

	@Autowired
	private InfopublicaFeedsLoader infoPublicaFeedsLoader;

	public TransportClient getElasticClient() throws UnknownHostException {
		return TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
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
					elasticSearchUpsert(item, entry.getKey(), client);
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
	
	public void elasticSearchUpsert(final Item item, String category, TransportClient client) throws IOException, InterruptedException, ExecutionException {
		GetResponse response = client.prepareGet("news", "new", "m-" + category + "-" + Integer.toString(item.getTitle().hashCode())).get();
		if(response.isExists() == false) {
			IndexRequest indexRequest = new IndexRequest("news", "new", "m-" + category + "-" + Integer.toString(item.getTitle().hashCode()))
			        .source(jsonBuilder()
			            .startObject()
			                .field("title", item.getTitle())
			                .field("description", item.getDescription())
			                .field("date", getPubDate(item.getPubDate()))
			                .field("link", item.getLink())
			            .endObject());
			client.index(indexRequest);
		}
//		IndexRequest indexRequest = new IndexRequest("news", "new", "m-" + category + "-" + Integer.toString(item.getTitle().hashCode()))
//						.source(jsonBuilder().startObject()
//							.field("title", item.getTitle())
//							.field("description", item.getDescription())
//							.field("date", getPubDate(item.getPubDate()))
//							.field("link", item.getLink())
//						.endObject());
//		UpdateRequest updateRequest = new UpdateRequest("news", "new", "m-" + category + "-" + Integer.toString(item.getTitle().hashCode()))
//		        .doc(jsonBuilder()
//		            .startObject()
//		            	.field("title", item.getTitle())
//		            	.field("description", item.getDescription())
//		            	.field("date", getPubDate(item.getPubDate()))
//		            	.field("link", item.getLink())
//		            .endObject())
//		        .upsert(indexRequest);              
//		client.update(updateRequest).get();
	}
	
	private long getPubDate(Date date) {
		if (date != null)
			return date.getTime();
		else
			return new Date().getTime();
	}
}
