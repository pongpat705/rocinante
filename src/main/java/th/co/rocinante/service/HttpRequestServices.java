package th.co.rocinante.service;

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

	
	public ChannelList getJoinedChannel(String peer) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<ChannelList> responseEntity = rest.exchange("http://192.168.43.170:4000/channels?peer="+peer, HttpMethod.GET, requestEntity, ChannelList.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public String[] getInstalledChaincode(String peer) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<String[]> responseEntity = rest.exchange("http://192.168.43.170:4000/chaincodes?peer="+peer+"&type=installed", HttpMethod.GET, requestEntity, String[].class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public String[] getInstantiatedChaincode(String peer) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getToken());
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
	    ResponseEntity<String[]> responseEntity = rest.exchange("http://192.168.43.170:4000/chaincodes?peer="+peer+"&type=instantiated", HttpMethod.GET, requestEntity, String[].class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public Enroll enrollUser(String user, String orgName) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/x-www-form-urlencoded");
	    MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
	    map.add("username", user);
	    map.add("orgName", orgName);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
	    ResponseEntity<Enroll> responseEntity = rest.exchange("http://192.168.43.170:4000/users", HttpMethod.POST, requestEntity, Enroll.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
	
	public MessageBean InstallingChaincode(String chaincodeName, String chaincodePath, String chaincodeType, String chaincodeVersion) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Bearer "+appParam.getToken());
	    InstallChaincode obj = new InstallChaincode();
	    obj.setPeers(appParam.getPeers());
	    obj.setChaincodeName(chaincodeName);
	    obj.setChaincodePath(chaincodePath);
	    obj.setChaincodeType(chaincodeType);
	    obj.setChaincodeVersion(chaincodeVersion);
	    
		HttpEntity<InstallChaincode> requestEntity = new HttpEntity<InstallChaincode>(obj, headers);
	    ResponseEntity<MessageBean> responseEntity = rest.exchange("http://192.168.43.170:4000/users", HttpMethod.POST, requestEntity, MessageBean.class);
	    if(!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
	    	
	    }
	    log.info( responseEntity.getBody().toString());
	    return responseEntity.getBody();
		
	}
}
