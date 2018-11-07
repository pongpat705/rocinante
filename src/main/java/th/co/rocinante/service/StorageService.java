package th.co.rocinante.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	static String localfolder = "/home/osboxes/hyperledger/fabric-samples/chaincode/upload";

	public String unzipAndKeep(MultipartFile file) {
		String result = "";
		try {
			File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
			FileOutputStream o = new FileOutputStream(zip);
			IOUtils.copy(file.getInputStream(), o);
			o.close();
			
			String foldername = file.getName();
			
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
			
			File uploadPath = new File(localfolder);
			
			log.info("path is exist : {} ({})", uploadPath.exists(), uploadPath.toPath().toString());
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
			String sChaincodePath = localfolder+"/"+foldername;
			File chaincodPath = new File(sChaincodePath);
			log.info("path is exist : {} ({})", chaincodPath.exists(), chaincodPath.toPath().toString());
			if(!chaincodPath.exists()) {
				chaincodPath.mkdirs();
			}
			if(chaincodPath.exists()) {
				byte[] buffer = new byte[1024];
				ZipEntry ze = zis.getNextEntry();
				while(ze!=null){
	    			
		    	   String fileName = ze.getName();
		           File newFile = new File(sChaincodePath + File.separator + fileName);
		                
		           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
		                
		            //create all non exists folders
		            //else you will hit FileNotFoundException for compressed folder
		            new File(newFile.getParent()).mkdirs();
		              
		            FileOutputStream fos = new FileOutputStream(newFile);             

		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		       		fos.write(buffer, 0, len);
		            }
		        		
		            fos.close();   
		            ze = zis.getNextEntry();
		            
			    }
			    	
			        zis.closeEntry();
			    	zis.close();
			    		
			}
			
		} catch (FileNotFoundException e) {
			result = e.getMessage();
			e.printStackTrace();
		} catch (ZipException e) {
			result = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		
		return result;
	}
}
