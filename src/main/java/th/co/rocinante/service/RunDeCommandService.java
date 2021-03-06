package th.co.rocinante.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import th.co.rocinante.bean.MessageBean;

@Service
public class RunDeCommandService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	
	public MessageBean run(String command) {
		log.info("running command : {}",command);
		MessageBean result = new MessageBean();
		
		List<String> output = new ArrayList<>();
		List<String> error = new ArrayList<>();
		String s = null;
		try {
            
		    // run the Unix "ps -ef" command
	            // using the Runtime exec method:
	            Process p = Runtime.getRuntime().exec(command);
	            
	            BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));

	            // read the output from the command
	            log.info("Here is the standard output of the command");
	            while ((s = stdInput.readLine()) != null) {
	            	output.add(s);
	            	System.out.println(s);
	            }
	            
	            // read any errors from the attempted command
	            log.info("Here is the standard error of the command (if any)");
	            while ((s = stdError.readLine()) != null) {
	            	error.add(s);
	            	System.out.println(s);
	            }
	            
	        }
	        catch (IOException e) {
	            log.info("exception happened - here's what I know");
	            e.printStackTrace();
	        }
		
		
		return result;
	}
	
	public MessageBean run(String[] command) {
		String cxmd = "";
		for (String string : command) {
			cxmd += string+" ";
		}
		log.info("running a command : {} ",cxmd);
		MessageBean result = new MessageBean();
		
		List<String> output = new ArrayList<>();
		List<String> error = new ArrayList<>();
		String s = null;
		try {
            
		    // run the Unix "ps -ef" command
	            // using the Runtime exec method:
	            Process p = Runtime.getRuntime().exec(command);
	            
	            BufferedReader stdInput = new BufferedReader(new 
	                 InputStreamReader(p.getInputStream()));

	            BufferedReader stdError = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));

	            // read the output from the command
	            log.info("Here is the standard output of the command");
	            while ((s = stdInput.readLine()) != null) {
	            	output.add(s);
	            	System.out.println(s);
	            }
	            
	            // read any errors from the attempted command
	            log.info("Here is the standard error of the command (if any)");
	            while ((s = stdError.readLine()) != null) {
	            	error.add(s);
	            	System.out.println(s);
	            }
	            
	        }
	        catch (IOException e) {
	            log.info("exception happened - here's what I know");
	            e.printStackTrace();
	        }
		
		
		return result;
	}

}
