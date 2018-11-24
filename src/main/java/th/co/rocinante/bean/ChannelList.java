package th.co.rocinante.bean;

import java.util.List;

public class ChannelList {

	@Override
	public String toString() {
		return "ChannelList [channels=" + channels + "]";
	}

	private List<Channel> channels;

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

}