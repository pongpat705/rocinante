package th.co.rocinante.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;

import th.co.rocinante.bean.MessageBean;

@Service
public class RunDeCommandService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	
	public MessageBean run(String command) {
		
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
		
		result.setError(error);
		result.setOutput(output);
		
		return result;
	}
	
	public void listContainners() throws DockerException, InterruptedException {
		final DockerClient docker = DefaultDockerClient.builder()
				  // Set various options
				.build();
		final List<Container> containers = docker.listContainers();
		for (Container container : containers) {
			log.info("image id : {} , image status: {}",container.id(),container.status());
		}
	}
	
	public MessageBean runSpotifyExec(String[] command) throws DockerException, InterruptedException {
		
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
		
		result.setError(error);
		result.setOutput(output);
		
		return result;
	}

}
