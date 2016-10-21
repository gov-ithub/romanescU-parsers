package infopublica.model;

public class RssFeed {

	private Channel channel;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "RssFeed [channel=" + channel + "]";
	}

}
