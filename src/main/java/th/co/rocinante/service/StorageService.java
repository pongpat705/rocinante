package th.co.rocinante.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import th.co.rocinante.AppConstant;
import th.co.rocinante.AppConstant.CHANNEL;
import th.co.rocinante.bean.MessageBean;
import th.co.rocinante.entity.ChainCode;
import th.co.rocinante.entity.ParamApp;
import th.co.rocinante.repository.ChainCodeRepository;
import th.co.rocinante.repository.ParamRepository;

@Service
public class StorageService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());	
	static String localfolder = "/home/osboxes/hyperledger/fabric-samples/chaincode/upload";
	
	@Autowired RunDeCommandService runDeCommand;
	@Autowired ChainCodeRepository chaincodeRepos;
	@Autowired ParamRepository paramRepos;
	
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
	
	@Transactional
	public String unzipAndKeepAndDeployChaincode(MultipartFile file, String chaincodeName, String version, String argument, String endorsePolicy) {
		String result = unzipAndKeep(file);
		if("0".equals(result)) {
			String foldername = getAFileName(file);
			String sChaincodePath = localfolder+"/"+foldername;
			
			ChainCode chainCode = new ChainCode();
			chainCode.setChaincodeName(chaincodeName);
			chainCode.setCreateDate(new Date());
			chainCode.setPath(sChaincodePath);
			chainCode.setVersion(version);
			
			chaincodeRepos.save(chainCode);
			List<String> orgs = new ArrayList<>();
			orgs.add(AppConstant.ORG.P0INDUSTRY);
			orgs.add(AppConstant.ORG.P1INDUSTRY);
			orgs.add(AppConstant.ORG.P0KRUNGTHAI);
			orgs.add(AppConstant.ORG.P1KRUNGTHAI);
			orgs.add(AppConstant.ORG.P0COMMERCE);
			orgs.add(AppConstant.ORG.P1COMMERCE);
			orgs.add(AppConstant.ORG.P0POLICE);
			orgs.add(AppConstant.ORG.P1POLICE);
			
			//export param && install chaincode
			String exportChannel = "docker exec -i cli export CHANNEL_NAME="+CHANNEL.CERT_CHANNEL;
			runDeCommand.run(exportChannel);
			for (String peer : orgs) {
				List<ParamApp> industyParams = paramRepos.findByGroupCode(peer);
				for (ParamApp param : industyParams) {
					String cmd = "docker exec cli export "+param.getCode()+"="+param.getData();
					MessageBean xx = runDeCommand.run(cmd);
					for (String e : xx.getOutput()) {
						log.info(e);
					}
					for (String e : xx.getError()) {
						log.info(e);
					}
				}
				String execInstallChaincode = "docker exec cli peer chaincode install -n "+chaincodeName+" -v "+version+" -p github.com/chaincode/upload/"+foldername;
				MessageBean xx = runDeCommand.run(execInstallChaincode);
				for (String e : xx.getOutput()) {
					log.info(e);
				}
				for (String e : xx.getError()) {
					log.info(e);
				}
			}
			
			String execIntantiated = "docker exec cli peer chaincode instantiate -o orderer.cert.com:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/cert.com/orderers/orderer.cert.com/msp/tlscacerts/tlsca.cert.com-cert.pem -C "+CHANNEL.CERT_CHANNEL+" -n "+chaincodeName+" -v "+version+" -c '"+argument+"' -P \""+endorsePolicy+"\"";
			MessageBean xx =runDeCommand.run(execIntantiated);
			for (String e : xx.getOutput()) {
				log.info(e);
			}
			for (String e : xx.getError()) {
				log.info(e);
			}
			
		}
		
		return result;
	}
	
	@Transactional
	public String unzipAndKeepAndDeployChaincodeViaScript(MultipartFile file, String chaincodeName, String version, String argument, String endorsePolicy) throws InterruptedException {
		String result = unzipAndKeep(file);
		if("0".equals(result)) {
			String foldername = getAFileName(file);
			String sChaincodePath = localfolder+"/"+foldername;
			
			String resultPeparingFile = peparingInstallScript(chaincodeName, version, foldername);
			if("0".equals(resultPeparingFile)) {
				
				String resultPeparingScript = peparingCommandeInstallInstantiatedScript(chaincodeName, version, foldername, argument, endorsePolicy, CHANNEL.CERT_CHANNEL);
				if("0".equals(resultPeparingScript)) {
					ChainCode chainCode = new ChainCode();
					chainCode.setChaincodeName(chaincodeName);
					chainCode.setCreateDate(new Date());
					chainCode.setPath(sChaincodePath);
					chainCode.setVersion(version);
					
					chaincodeRepos.save(chainCode);
					List<String> orgs = new ArrayList<>();
					orgs.add(AppConstant.ORG.P0INDUSTRY);
					orgs.add(AppConstant.ORG.P1INDUSTRY);
					orgs.add(AppConstant.ORG.P0KRUNGTHAI);
					orgs.add(AppConstant.ORG.P1KRUNGTHAI);
					orgs.add(AppConstant.ORG.P0COMMERCE);
					orgs.add(AppConstant.ORG.P1COMMERCE);
					orgs.add(AppConstant.ORG.P0POLICE);
					orgs.add(AppConstant.ORG.P1POLICE);
					
					String mode = "instantiated";
					if(!"1.0".equals(version)) {
						mode = "upgraded";
					}
					//export param && install chaincode
					String[] runScript = {"./home/osboxes/hyperledger/fabric-samples/CerT/scripts/install-"+mode+"-"+chaincodeName+version+".sh"};
					MessageBean rs = runDeCommand.run(runScript);
					for (String e : rs.getOutput()) {
						log.info(e);
					}
					for (String e : rs.getError()) {
						log.info(e);
					}
				}
				
			}
			
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
			for (String e : xx.getOutput()) {
				log.info(e);
			}
			for (String e : xx.getError()) {
				result = e+"/n";
				log.info(e);
			}
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
			content = content.replaceAll("\\{scriptfile\\}", "./scripts/install-chaincode-certchannel-"+chaincodeName+version+".sh");
			content = content.replaceAll("\\{channel\\}", channel);
			content = content.replaceAll("\\{version\\}", version);
			content = content.replaceAll("\\{argument\\}", argument);
			content = content.replaceAll("\\{endorse_policy\\}", endorsPolicy);
			String destinationPath = "/home/osboxes/hyperledger/fabric-samples/CerT/scripts/install-"+mode+"-"+chaincodeName+version+".sh";
			org.apache.commons.io.FileUtils.writeStringToFile(new File(destinationPath), content);
			File file = new File(destinationPath);
			if(!file.exists()) {
				result = "file not found";
			}
			String[] chmodCmd = {"chmod","u+x",destinationPath};
			MessageBean xx = runDeCommand.run(chmodCmd);
			
			Thread.sleep(4000);
			for (String e : xx.getOutput()) {
				log.info(e);
			}
			for (String e : xx.getError()) {
				result = e+"/n";
				log.info(e);
			}
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
