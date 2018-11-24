package th.co.rocinante.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			MessageBean message = httpServices.InstallingChaincode(chaincodeName, "upload/"+codeFolder, type, version);
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
	public String instantiate(@RequestParam String chaincodeTxt){
		String name = chaincodeTxt;
		String[] artifacts = name.split(",");
		for (int i = 0; i < artifacts.length; i++) {
			String s = artifacts[i];
			int index = s.indexOf(":");
			String piece = s.substring(index+1);
			log.info(piece.trim());
		}
		
		return "index";
	}
	
}
