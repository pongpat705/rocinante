package th.co.rocinante.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.ZipException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import th.co.rocinante.bean.MessageBean;

@Service
public class StorageService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	static String localfolder = "/home/hyperledger/fabric-samples/cert-hyperledger-network/artifacts/src/upload";
	
	@Autowired RunDeCommandService runDeCommand;
	
	public String unzipAndKeep(MultipartFile file) {
		String result = "0";
		try {
			File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
			FileOutputStream o = new FileOutputStream(zip);
			IOUtils.copy(file.getInputStream(), o);
			o.close();
			
			String foldername = getAFileName(file);
			
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
				File fClean = new File(sChaincodePath);
				FileUtils.cleanDirectory(fClean);
				ZipFile zipFile = new ZipFile(zip);
		        zipFile.extractAll(sChaincodePath);
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
		} catch (net.lingala.zip4j.exception.ZipException e) {
			result = e.getMessage();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String peparingInstallScript(String chaincodeName, String version, String foldername) throws InterruptedException {
		String result = "0";
		try {
			String content;
			content = readFile("/home/osboxes/hyperledger/fabric-samples/CerT/scripts/install-chaincode-certchannel-template.sh", Charset.defaultCharset());
			content = content.replaceAll("\\{chaincode_name\\}", chaincodeName);
			content = content.replaceAll("\\{folder_name\\}", foldername);
			content = content.replaceAll("\\{version\\}", version);
			String destinationPath = "/home/osboxes/hyperledger/fabric-samples/CerT/scripts/install-chaincode-certchannel-"+chaincodeName+version+".sh";
			org.apache.commons.io.FileUtils.writeStringToFile(new File(destinationPath), content);
			File file = new File(destinationPath);
			if(!file.exists()) {
				result = "file not found";
			}
			String[] chmodCmd = {"chmod","u+x",destinationPath};
			MessageBean xx = runDeCommand.run(chmodCmd);
			
			Thread.sleep(4000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String peparingCommandeInstallInstantiatedScript(String chaincodeName, String version, String foldername, String argument, String endorsPolicy, String channel) throws InterruptedException {
		String result = "0";
		String mode = "instantiated";
		if(!"1.0".equals(version)) {
			mode = "upgraded";
		}
		try {
			String content;
			content = readFile("/home/osboxes/hyperledger/fabric-samples/CerT/scripts/install-"+mode+"-template.sh", Charset.defaultCharset());
			content = content.replaceAll("\\{scriptfile\\}", "\"./scripts/install-chaincode-certchannel-"+chaincodeName+version+".sh\"");
			content = content.replaceAll("\\{channel\\}", channel);
			content = content.replaceAll("\\{version\\}", version);
			content = content.replaceAll("\\{argument\\}", "'"+argument+"'");
			content = content.replaceAll("\\{chaincode_name\\}", chaincodeName);
			content = content.replaceAll("\\{endorse_policy\\}", "\""+endorsPolicy+"\"");
			String destinationPath = "/home/osboxes/hyperledger/fabric-samples/CerT/scripts/install-"+mode+"-"+chaincodeName+version+".sh";
			org.apache.commons.io.FileUtils.writeStringToFile(new File(destinationPath), content);
			File file = new File(destinationPath);
			if(!file.exists()) {
				result = "file not found";
			}
			String[] chmodCmd = {"chmod","u+x",destinationPath};
			MessageBean xx = runDeCommand.run(chmodCmd);
			
			Thread.sleep(4000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String readFile(String path, Charset encoding)  throws IOException  {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public String getAFileName(MultipartFile file) {
		String foldername = file.getOriginalFilename();
		return foldername.replaceAll(".zip", "");
	}
}
