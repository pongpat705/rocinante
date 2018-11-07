package th.co.rocinante.bean;

import java.util.List;

public class MessageBean {

	private List<String> output;
	private List<String> error;
	
	public List<String> getOutput() {
		return output;
	}
	public List<String> getError() {
		return error;
	}
	public void setOutput(List<String> output) {
		this.output = output;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	
	
}
