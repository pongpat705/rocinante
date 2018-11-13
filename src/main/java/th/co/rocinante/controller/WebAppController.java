package th.co.rocinante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import th.co.rocinante.bean.MessageBean;
import th.co.rocinante.service.RunDeCommandService;
import th.co.rocinante.service.StorageService;

@Controller
public class WebAppController {

	@Autowired RunDeCommandService runExec;
	@Autowired StorageService storageService;
	
	@RequestMapping("/")
	public String index(Model model){
		
		
		MessageBean messageBean = runExec.run("docker ps -a");
		
		model.addAttribute("dockerContainers", messageBean);
		model.addAttribute("result", "");
		
		return "index";
	}
	
	@GetMapping("/upload")
	public String uploadPage(){
		
		
		return "upload";
	}
	
	@PostMapping("/upload")
	public String upload(Model model, @RequestParam("file") MultipartFile file, @RequestParam("chaincodeName") String chaincodeName,
			@RequestParam("version") String version, @RequestParam("argument") String argument,
			@RequestParam("endorsePolicy") String endorsePolicy,
            RedirectAttributes redirectAttributes){
		
		String result = "";
		
		try {
			
			result = storageService.unzipAndKeepAndDeployChaincodeViaScript(file, chaincodeName, version, argument, endorsePolicy);
			
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		
		redirectAttributes.addAttribute("result", result);
		
		return "redirect:/";
	}
	
}
