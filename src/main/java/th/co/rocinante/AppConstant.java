package th.co.rocinante;

public class AppConstant {

	public static class BASE_COMMAND {
		public static String SPACE =" ";
		public static String CLI = "docker exec -it cli";
		public static String EXPORT ="export";
	}
	
	public static class ORG {
		public static String INDUSTRY = "INDUSTRY";
		public static String KRUNGTHAI = "KRUNGTHAI";
		public static String COMMERCE = "COMMERCE";
		public static String POLICE = "POLICE";
	}
	
	public static class CHANNEL {
		public static String CERT_CHANNEL = "certchannel";
	}
	
	public static class ENVIROMENT_PARAM {
		public static String CORE_PEER_MSCONFIGPATH = "CORE_PEER_MSCONFIGPATH";
		public static String CORE_PEER_ADDRESS = "CORE_PEER_ADDRESS";
		public static String CORE_PEER_LOCALMSPID = "CORE_PEER_LOCALMSPID";
		public static String CORE_PEER_TLS_ROOTCERT_FILE = "CORE_PEER_TLS_ROOTCERT_FILE";
		public static String CHANNEL_NAME = "CHANNEL_NAME";
	}
}
