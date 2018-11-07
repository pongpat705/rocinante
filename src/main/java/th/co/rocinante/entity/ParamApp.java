package th.co.rocinante.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PARAM_APP")
public class ParamApp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String group;
	private String code;
	private String data;
	public Long getId() {
		return id;
	}
	public String getGroup() {
		return group;
	}
	public String getCode() {
		return code;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
