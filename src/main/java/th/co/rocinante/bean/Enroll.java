package th.co.rocinante.bean;

public class Enroll {

	@Override
	public String toString() {
		return "Enroll [success=" + success + ", secret=" + secret + ", message=" + message + ", token=" + token + "]";
	}

	private Boolean success;
	private String secret;
	private String message;
	private String token;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}