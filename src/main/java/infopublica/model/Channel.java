package infopublica.model;

import java.util.List;

public class Channel {

	private String title;
	private String link;
	private String description;
	private List<Item> items;

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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Channel [title=" + title + ", link=" + link + ", description="
				+ description + ", items=" + items + "]";
	}
}
