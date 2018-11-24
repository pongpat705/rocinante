package th.co.rocinante.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "channel_id" })
public class Channel {

	@JsonProperty("channel_id")
	private String channelId;

	@JsonProperty("channel_id")
	public String getChannelId() {
		return channelId;
	}

	@JsonProperty("channel_id")
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}