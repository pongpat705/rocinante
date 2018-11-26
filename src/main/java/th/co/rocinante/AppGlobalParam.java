package th.co.rocinante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import th.co.rocinante.bean.APIParam;
import th.co.rocinante.bean.Enroll;
import th.co.rocinante.service.HttpRequestServices;

@Component
public class AppGlobalParam {
	
	@Value("${org.user}") 
	private String user;
	@Value("${channel}")
	private String channel;
	
	private List<String> orgNames;
	
	private List<APIParam> params;
	
	private Map<String, APIParam> apiParam;
	
	@Autowired private HttpRequestServices httpServices;
	
	@PostConstruct
	public void EnrollUser() {
		apiParam = new HashMap<>();
		orgNames = new ArrayList<>();
		orgNames.add("Ind");
		orgNames.add("Ktb");
		orgNames.add("Cml");
		orgNames.add("Pol");
		
		params = new ArrayList<>();
		//Ind peers
		APIParam paramInd = new APIParam();
		String[] peersInd = {"peer0.ind.cert.com","peer1.ind.cert.com"};
		paramInd.setPeers(peersInd);
		paramInd.setOrgName(orgNames.get(0));
		params.add(paramInd);
		apiParam.put(orgNames.get(0), paramInd);
		
		//Ktb peers
		APIParam paramKtb = new APIParam();
		String[] peersKtb = {"peer0.ktb.cert.com","peer1.ktb.cert.com"};
		paramKtb.setPeers(peersKtb);
		paramKtb.setOrgName(orgNames.get(1));
		params.add(paramKtb);
		apiParam.put(orgNames.get(1), paramKtb);
		
		//Cml peers
		APIParam paramCml = new APIParam();
		String[] peersCml = {"peer0.cml.cert.com","peer1.cml.cert.com"};
		paramCml.setPeers(peersCml);
		paramCml.setOrgName(orgNames.get(2));
		params.add(paramCml);
		apiParam.put(orgNames.get(2), paramCml);
		
		//Cml peers
		APIParam paramPol = new APIParam();
		String[] peersPol = {"peer0.pol.cert.com","peer1.pol.cert.com"};
		paramPol.setPeers(peersPol);
		paramPol.setOrgName(orgNames.get(3));
		params.add(paramPol);
		apiParam.put(orgNames.get(3), paramPol);
		
		for (APIParam a : params) {
			Enroll enroll = httpServices.enrollUser(user, a.getOrgName());
			if(enroll.getSuccess()) {
				a.setToken(enroll.getToken());
				apiParam.get(a.getOrgName()).setToken(enroll.getToken());
			}
		}
		
		
	}

	public List<APIParam> getParams() {
		return params;
	}

	public String getUser() {
		return user;
	}

	public String getChannel() {
		return channel;
	}

	public List<String> getOrgNames() {
		return orgNames;
	}

	public Map<String, APIParam> getApiParam() {
		return apiParam;
	}


}
