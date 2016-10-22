package infopublica.model;

import java.util.Date;

public class Item {

	private String title;
	private String link;
	private String description;
	private Date pubDate;
	private Date retrievedDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getRetrievedDate() {
		return retrievedDate;
	}

	public void setRetrievedDate(Date retrievedDate) {
		this.retrievedDate = retrievedDate;
	}

	@Override
	public String toString() {
		return "Item [title=" + title + ", link=" + link + ", description="
				+ description + ", pubDate=" + pubDate + ", retrievedDate="
				+ retrievedDate + "]";
	}
}
