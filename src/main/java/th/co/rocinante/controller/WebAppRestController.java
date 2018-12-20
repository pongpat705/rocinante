package th.co.rocinante.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import th.co.rocinante.AppGlobalParam;

@RestController
@RequestMapping("/api")
public class WebAppRestController {

	@Autowired AppGlobalParam appGlobal;
	
	@RequestMapping(value="/reload/token", method=RequestMethod.GET)
	public Map<String, String> reloadToken(){
		Map<String, String> result = new HashMap<>();
		try {
			appGlobal.EnrollUser();
			result.put("response", "OK");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("response", e.getMessage());
		}
		
		return result;
		 
	}
}
