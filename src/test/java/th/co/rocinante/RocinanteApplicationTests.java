package th.co.rocinante;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import th.co.rocinante.service.StorageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RocinanteApplicationTests {
	
	@Autowired StorageService storageService;

	@Test
	public void contextLoads() {
//		String content;
//		try {
//			content = storageService.readFile("C:\\test-replace.txt", Charset.defaultCharset());
//			content = content.replaceAll("\\{chaincode_name\\}", "rocinante");
//			content = content.replaceAll("\\{folder_name\\}", "rocinante");
//			content = content.replaceAll("\\{version\\}", "2.0");
//			FileUtils.writeStringToFile(new File("C:\\Users\\pongpat\\Documents\\replaced.txt"), content);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
