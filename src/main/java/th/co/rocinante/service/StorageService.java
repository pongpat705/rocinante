package th.co.rocinante.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.zip.ZipException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import th.co.rocinante.entity.ChainCode;
import th.co.rocinante.repository.ChainCodeRepository;

@Service
public class StorageService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	static String localfolder = "/home/osboxes/hyperledger/fabric-samples/chaincode/upload";
	
	@Autowired RunDeCommandService runDeCommand;
	@Autowired ChainCodeRepository chaincodeRepos;
	
	public String unzipAndKeep(MultipartFile file) {
		String result = "0";
		try {
			File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
			FileOutputStream o = new FileOutputStream(zip);
			IOUtils.copy(file.getInputStream(), o);
			o.close();
			
			String foldername = file.getOriginalFilename();
			foldername = foldername.replaceAll(".zip", "");
			
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
	
	@Transactional
	public String unzipAndKeepAndDeployChaincode(MultipartFile file, String chaincodeName, String version) {
		String result = unzipAndKeep(file);
		if("0".equals(result)) {
			String foldername = file.getOriginalFilename();
			foldername = foldername.replaceAll(".zip", "");
			String sChaincodePath = localfolder+"/"+foldername;
			
			ChainCode chainCode = new ChainCode();
			chainCode.setChaincodeName(chaincodeName);
			chainCode.setCreateDate(new Date());
			chainCode.setPath(sChaincodePath);
			chainCode.setVersion(version);
			
			chaincodeRepos.save(chainCode);
			
		}
		
		return result;
	}
}
