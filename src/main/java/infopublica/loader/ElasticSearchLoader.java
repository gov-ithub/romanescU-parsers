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
import org.elasticsearch.action.update.UpdateResponse;
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
		
//		if(!checkIndexExists("news", client)) {
//			createIndex("news", client);
//		}
		
		try {
			client = getElasticClient();
		} catch (UnknownHostException ex) {
			System.out.println("Can't get Elastic Search connection!");
		}
		int sum = 0;
		int count = 0;
		System.out.println("Number of feeds to insert news from: " + feedsList.size());
		for (Entry<String, RssFeed> entry : feedsList.entrySet()) {
			count = 0;
			List<Item> items = entry.getValue().getChannel().getItems();
			System.out.println("Inserting " + items.size() + " for this feed: " + entry.getKey());
			for (Item item : items) {
				try {
					elasticSearchInsert(item, entry.getKey(), client);
					count++;
				} catch (Exception ex) {
					System.out.println("Exception while inserting into Elastic Search");
					System.out.println("Exception: " + ex.getMessage());
				}
			}
			sum += count;
		}
		System.out.println("Number of news: " + sum);
		client.close();
	}
	
//	private void createIndex(String index, TransportClient client) {
//		client.admin().indices().create(Requests.createIndexRequest(index)).actionGet();
//	}
//
//	private boolean checkIndexExists(String index, TransportClient client) {
//		return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
//	}

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
		UpdateResponse updateResponse = client.update(updateRequest).get();
		System.out.println("m-" + category + "-" + Integer.toString(item.getTitle().hashCode()) + " created: " +  updateResponse.isCreated());
	}
	
	private long getPubDate(Date date) {
		if (date != null)
			return date.getTime();
		else
			return new Date().getTime();
	}
}
