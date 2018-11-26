package th.co.rocinante.bean;

public class APIParam {
	private String orgName;
	private String[] peers;
	private String token;
	
	public String[] getPeers() {
		return peers;
	}
	public String getToken() {
		return token;
	}
	public void setPeers(String[] peers) {
		this.peers = peers;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
