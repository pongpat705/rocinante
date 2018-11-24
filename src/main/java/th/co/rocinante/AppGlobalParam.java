package th.co.rocinante;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import th.co.rocinante.bean.Enroll;
import th.co.rocinante.service.HttpRequestServices;

@Component
public class AppGlobalParam {
	
	String token;
	@Value("${org.user}") 
	String user;
	@Value("${org.name}") 
	String orgName;
	@Value("${channel}")
	String channel;
	@Value("${org.peers}") 
	String[] peers;
	
	@Autowired private HttpRequestServices httpServices;
	
	@PostConstruct
	public void EnrollUser() {
		
		Enroll enroll = httpServices.enrollUser(user, orgName);
		if(enroll.getSuccess()) {
			token = enroll.getToken();
		}
		
	}

	public String getToken() {
		return token;
	}

	public String getUser() {
		return user;
	}

	public String getOrgName() {
		return orgName;
	}

	public String[] getPeers() {
		return peers;
	}

	public String getChannel() {
		return channel;
	}


}
