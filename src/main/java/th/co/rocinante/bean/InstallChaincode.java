package th.co.rocinante.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "peers", "chaincodeName", "chaincodePath", "chaincodeType", "chaincodeVersion" })
public class InstallChaincode {

	@JsonProperty("peers")
	private String[] peers;
	@JsonProperty("chaincodeName")
	private String chaincodeName;
	@JsonProperty("chaincodePath")
	private String chaincodePath;
	@JsonProperty("chaincodeType")
	private String chaincodeType;
	@JsonProperty("chaincodeVersion")
	private String chaincodeVersion;

	@JsonProperty("peers")
	public String[] getPeers() {
		return peers;
	}

	@JsonProperty("peers")
	public void setPeers(String[] peers) {
		this.peers = peers;
	}

	@JsonProperty("chaincodeName")
	public String getChaincodeName() {
		return chaincodeName;
	}

	@JsonProperty("chaincodeName")
	public void setChaincodeName(String chaincodeName) {
		this.chaincodeName = chaincodeName;
	}

	@JsonProperty("chaincodePath")
	public String getChaincodePath() {
		return chaincodePath;
	}

	@JsonProperty("chaincodePath")
	public void setChaincodePath(String chaincodePath) {
		this.chaincodePath = chaincodePath;
	}

	@JsonProperty("chaincodeType")
	public String getChaincodeType() {
		return chaincodeType;
	}

	@JsonProperty("chaincodeType")
	public void setChaincodeType(String chaincodeType) {
		this.chaincodeType = chaincodeType;
	}

	@JsonProperty("chaincodeVersion")
	public String getChaincodeVersion() {
		return chaincodeVersion;
	}

	@JsonProperty("chaincodeVersion")
	public void setChaincodeVersion(String chaincodeVersion) {
		this.chaincodeVersion = chaincodeVersion;
	}

}