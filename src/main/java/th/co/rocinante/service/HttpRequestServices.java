package th.co.rocinante.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import th.co.rocinante.AppGlobalParam;
import th.co.rocinante.bean.ChannelList;
import th.co.rocinante.bean.Enroll;
import th.co.rocinante.bean.InstallChaincode;
import th.co.rocinante.bean.MessageBean;

@Service
public class HttpRequestServices {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired AppGlobalParam appParam;

	
	public ChannelList getJoinedChannel(String peer, String org) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<ChannelList> responseEntity = rest.exchange("http://localhost:4000/channels?peer="+peer, HttpMethod.GET, requestEntity, ChannelList.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public String[] getInstalledChaincode(String peer, String org) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<String[]> responseEntity = rest.exchange("http://localhost:4000/chaincodes?peer="+peer+"&type=installed", HttpMethod.GET, requestEntity, String[].class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public String[] getInstantiatedChaincode(String peer, String org) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<String[]> responseEntity = rest.exchange("http://localhost:4000/channels/"+appParam.getChannel()+"/chaincodes?peer="+peer, HttpMethod.GET, requestEntity, String[].class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public void createAndJoinChannel(String channelName, String channelPath) {
		RestTemplate rest = new RestTemplate();
		
		List<String> orgNames = appParam.getOrgNames();
		for (int i = 0; i < orgNames.size(); i++) {
			String org = orgNames.get(i);
			if(i == 0) {
			
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
			    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			    map.add("channelName", channelName);
			    map.add("channelConfigPath", channelPath);
				HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			    ResponseEntity<MessageBean> responseEntity = rest.exchange("http://localhost:4000/channels", HttpMethod.POST, requestEntity, MessageBean.class);
			    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			    	
			    }
			    
			}
			
			String[] peersInd = {"peer0.ind.cert.com","peer1.ind.cert.com"};
			String[] peersKtb = {"peer0.ktb.cert.com","peer1.ktb.cert.com"};
			String[] peersCml = {"peer0.cml.cert.com","peer1.cml.cert.com"};
			String[] peersPol = {"peer0.pol.cert.com","peer1.pol.cert.com"};
			
			String[] peer = null;
			if(org.equals("Ind")) peer = peersInd;
			if(org.equals("Ktb")) peer = peersKtb;
			if(org.equals("Cml")) peer = peersCml;
			if(org.equals("Pol")) peer = peersPol;
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
		    MultiValueMap<String, String[]> map= new LinkedMultiValueMap<String, String[]>();
		    map.add("peers", peer);
			HttpEntity<MultiValueMap<String, String[]>> requestEntity = new HttpEntity<MultiValueMap<String, String[]>>(map, headers);
		    ResponseEntity<MessageBean> responseEntity = rest.exchange("http://localhost:4000/channels/"+channelName+"/peers", HttpMethod.POST, requestEntity, MessageBean.class);
		    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
		    	
		    }
			
		}
		
	}
	
	public Enroll enrollUser(String user, String orgName) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/x-www-form-urlencoded");
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    map.add("username", user);
	    map.add("orgName", orgName);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	    ResponseEntity<Enroll> responseEntity = rest.exchange("http://localhost:4000/users", HttpMethod.POST, requestEntity, Enroll.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public MessageBean installingChaincode(String chaincodeName, String chaincodePath, String chaincodeType, String chaincodeVersion, String org) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
	    InstallChaincode obj = new InstallChaincode();
	    obj.setPeers(appParam.getApiParam().get(org).getPeers());
	    obj.setChaincodeName(chaincodeName);
	    obj.setChaincodePath(chaincodePath);
	    obj.setChaincodeType(chaincodeType);
	    obj.setChaincodeVersion(chaincodeVersion);
	    
		HttpEntity<InstallChaincode> requestEntity = new HttpEntity<InstallChaincode>(obj, headers);
	    ResponseEntity<MessageBean> responseEntity = rest.exchange("http://localhost:4000/chaincodes", HttpMethod.POST, requestEntity, MessageBean.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public MessageBean instantiatedChaincode(Map<String, Object> value, String org) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getApiParam().get(org).getToken());
	    
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(value, headers);
	    ResponseEntity<MessageBean> responseEntity = rest.exchange("http://localhost:4000/channels/"+appParam.getChannel()+"/chaincodes", HttpMethod.POST, requestEntity, MessageBean.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
}
