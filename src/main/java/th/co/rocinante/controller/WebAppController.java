package th.co.rocinante.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.rocinante.AppGlobalParam;
import th.co.rocinante.bean.ChannelList;
import th.co.rocinante.bean.MessageBean;
import th.co.rocinante.service.HttpRequestServices;
import th.co.rocinante.service.StorageService;

@Controller
public class WebAppController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired StorageService storageService;
	@Autowired HttpRequestServices httpServices;
	@Autowired AppGlobalParam appGlobal;
	
	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("peers", appGlobal.getPeers());
		model.addAttribute("orgName", appGlobal.getOrgName());
		return "index";
	}
	
	@GetMapping("/channels")
	public String channels(Model model, @RequestParam String peer) {
		ChannelList channelList = httpServices.getJoinedChannel(peer);
		String[] installedChaincodes = httpServices.getInstalledChaincode(peer);
		String[] instantiatedChaincodes = httpServices.getInstantiatedChaincode(peer);
		model.addAttribute("channelList", channelList);
		model.addAttribute("installedChaincodes", installedChaincodes);
		model.addAttribute("instantiatedChaincodes", instantiatedChaincodes);
		model.addAttribute("peer", peer);
		return "channel";
	}
	
	@GetMapping("/upload")
	public String uploadPage(){
		
		
		return "upload";
	}
	
	@PostMapping("/upload")
	public String upload(Model model, @RequestParam("file") MultipartFile file, @RequestParam("chaincodeName") String chaincodeName,
			@RequestParam("version") String version, @RequestParam("type") String type,
            RedirectAttributes redirectAttributes){
		
		String result = "";
		
		try {
			
			result = storageService.unzipAndKeep(file);
			String codeFolder = storageService.getAFileName(file);
			MessageBean message = httpServices.installingChaincode(chaincodeName, "upload/"+codeFolder, type, version);
			if(!message.getSuccess()) {
				throw new Exception(message.getMessage());
			}
			
			
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		
		redirectAttributes.addAttribute("result", result);
		
		return "redirect:/";
	}
	
	@GetMapping("/instantiate")
	public String instantiate(@RequestParam String chaincodeTxt, @RequestParam String peer, Model model){
		String name = chaincodeTxt;
		String[] artifacts = name.split(",");
		Map<String, String> params = new HashMap<>();
		for (int i = 0; i < artifacts.length; i++) {
			String s = artifacts[i];
			int index = s.indexOf(":");
			String piece = s.substring(index+1);
			piece = piece.trim();
			log.info(piece);
			
			switch (i) {
			case 0:
				params.put("chaincodeName", piece);
				break;
			case 1:
				params.put("chaincodeVersion", piece);
				break;
			default:
				break;
			}
		}
		params.put("chaincodeType", "golang");
		params.put("peers", peer);
		model.addAllAttributes(params);
		
		return "instantiate";
	}
	
	@RequestMapping(value="/instantiate", 
            method=RequestMethod.POST, 
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String instantiatePost(@RequestBody MultiValueMap<String, String> formData) throws IOException{
		Map<String, Object> toJson = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		
		for (String k : formData.keySet()) {
			if("args".equals(k) || "peers".equals(k)) {
				String[] param = formData.getFirst(k).split(",");
				toJson.put(k, param);
			} else if("endorsePolicy".equals(k)){
				String param = formData.getFirst(k);
				TypeReference<HashMap<String,Object>> typeRef  = new TypeReference<HashMap<String,Object>>() {};
				Map<String, Object> xx = mapper.readValue(param, typeRef);
				toJson.put(k, xx);
			}else {
				toJson.put(k, formData.getFirst(k));
			}
			
		}
		String xx = mapper.writeValueAsString(toJson);
		log.info(xx);
		
		MessageBean message = httpServices.instantiatedChaincode(toJson);
			
		
		return "redirect:/";
	}
	
}
